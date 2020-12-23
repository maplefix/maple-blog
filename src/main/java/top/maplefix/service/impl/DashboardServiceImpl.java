package top.maplefix.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.maplefix.constant.Constant;
import top.maplefix.mapper.DashboardMapper;
import top.maplefix.model.*;
import top.maplefix.redis.CacheExpire;
import top.maplefix.redis.TimeType;
import top.maplefix.service.DashboardService;
import top.maplefix.service.DictDataService;
import top.maplefix.service.LinkService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.vo.LineChartData;
import top.maplefix.vo.home.AccessData;
import top.maplefix.vo.home.AccessDataItem;
import top.maplefix.vo.home.AccessDataResult;
import top.maplefix.vo.home.KeyValue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author : Maple
 * @description : dashboard面板service实现类
 * @date : 2020/2/15 18:08
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private static final Pattern pattern = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.xyz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");

    @Autowired
    DashboardMapper dashBoardMapper;
    @Autowired
    private DictDataService dictDataService;
    @Autowired
    private LinkService linkService;

    private static final String INTERNAL_KEY = "maplefix.top";

    @Override
    public Map<String, Long> getPanelGroupData() {
        Map<String, Long> resultMap = new HashMap<>();
        Long visitorCount = dashBoardMapper.getVisitorCount();
        Long blogCount = dashBoardMapper.getBlogCount();
        resultMap.put("visitorCount", visitorCount);
        resultMap.put("blogCount", blogCount);
        return resultMap;
    }

    @Override
    @Cacheable(value = "DashBoard", key = "'LineChartData'+ #type")
    @CacheExpire(expire = 3, type = TimeType.HOURS)
    public LineChartData<Long> getLineChartData(String type) {
        LineChartData lineChartData;
        switch (type) {
            case LineChartData.BLOG_LINE:
                lineChartData = getBlogLineChartData();
                break;
            case LineChartData.VISITOR_LINE:
                lineChartData = getVisitorLineChartData();
                break;
            default:
                lineChartData = new LineChartData();
        }
        //log.info("get line chart data \n{}", lineChartData);
        return lineChartData;
    }

    @Override
    @Cacheable(value = "DashBoard", key = "'SpiderData'")
    public List<Map<String, Long>> getSpiderData() {
        return dashBoardMapper.getSpiderData();
    }

    @Override
    public List<String> getVisitLogStringList() {
        List<VisitLog> visitLogList = dashBoardMapper.getVisitLog();
        List<String> result = new ArrayList<>();
        for (VisitLog visitLog : visitLogList) {
            String name = "";
            if (visitLog.getPageId() != null) {
                if (visitLog.getPageId() == -1000) {
                    name = "留言页";
                } else {
                    Long pageId = visitLog.getPageId();
                    if (pageId != null) {
                        name = dashBoardMapper.getBlogNameByPageId(pageId);
                    }
                }
            }
            //XXX 分钟前 : @47.112.14.207 浏览了 <strong>title</strong> <a href='url'>XXXXX</a>
            result.add(StringUtils.format("{} : @{} 浏览了 <strong> {} </strong> <a href='{}'> {} </a>",
                    DateUtils.showTime(DateUtils.parseDate(visitLog.getCreateDate())), visitLog.getIp(), visitLog.getModule(),
                    visitLog.getUrl(), StringUtils.isEmpty(name) ? visitLog.getUrl() : name));
        }
        return result;
    }

    @Override
    public List<String> getLoginLogStringList() {
        List<LoginLog> loginLogList = dashBoardMapper.getLoginLogList();
        List<String> result = new LinkedList<>();
        for (LoginLog loginLog : loginLogList) {
//            XXX 分钟前: XXXX 登录系统 XXXX
            result.add(StringUtils.format("{} : {} 登录系统 {}{}",
                    DateUtils.showTime(DateUtils.parseDate(loginLog.getCreateDate())), loginLog.getUserName(),
                    loginLog.getStatus().booleanValue() ? "成功" : "失败", loginLog.getStatus().booleanValue() ? "" : "异常信息:" + loginLog.getMsg()));
        }
        return result;
    }

    @Override
    public List<String> getOperateLogStringList() {
        List<OperateLog> operateLogList = dashBoardMapper.getOperateLogList();
        List<String> result = new LinkedList<>();
        Map<String, String> operateTypeString = getOperateTypeString();
        for (OperateLog operateLog : operateLogList) {
            String businessType = operateLog.getBusinessType();
            String typeString = operateTypeString.get(businessType.toString());
            result.add(StringUtils.format("{} : {} 对 {} 进行 {} 操作 {} {}",
                    DateUtils.showTime(DateUtils.parseDate(operateLog.getCreateDate())), operateLog.getOperateName(),
                    operateLog.getModule(), businessType, operateLog.getStatus().booleanValue() ? "成功" : "失败",
                    operateLog.getStatus().booleanValue() ? "" : ",异常信息:" + operateLog.getErrorMsg()));
        }
        return result;
    }

    private Map<String, String> getOperateTypeString() {
        List<DictData> operateTypeList = dictDataService.selectDictDataByType("sys_oper_type");
        return operateTypeList.stream().collect(Collectors.toMap(DictData::getDictValue, DictData::getDictLabel));
    }


    @Override
    public List<String> getTaskLogStringList() {
        List<String> result = new LinkedList<>();
        List<JobLog> jobLogList = dashBoardMapper.getJobLogList();
        for (JobLog quartzJobLog : jobLogList) {
            result.add(StringUtils.format("{} : {} 执行 {}", DateUtils.showTime(DateUtils.parseDate(quartzJobLog.getCreateDate())),
                    quartzJobLog.getJobName(), quartzJobLog.getStatus().booleanValue() ? "成功" : "失败"));
        }
        return result;
    }

    /**
     * 获取url的顶级域名
     *
     * @param
     * @return
     */
    public static String getTopDomain(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            //获取值转换为小写
            String host = new URL(url).getHost().toLowerCase();
            Matcher matcher = pattern.matcher(host);
            while (matcher.find()) {
                return matcher.group();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AccessDataResult getAccessData() {
        List<KeyValue> accessDataList = dashBoardMapper.getAccessData();
        if (accessDataList.isEmpty()) {
            return new AccessDataResult();
        }
        //handle data
        accessDataList = accessDataList.stream().map(e -> new KeyValue(getTopDomain(e.getKey()), e.getValue())).collect(Collectors.toList());
        AccessData direct = new AccessData("直达", 0);
        AccessData localRedirect = new AccessData("本地跳转", 0);
        AccessData searchEngine = new AccessData("搜索引擎", 0);
        AccessData link = new AccessData("友链跳转", 0);
        AccessData other = new AccessData("其他", 0);
        //搜索引擎
        List<AccessDataItem> searchEngineAccessDataItemList = getSearchEngineAccessDataItemList();
        //友链跳转
        List<AccessDataItem> linkAccessDataItemList = getLinkAccessDataItemList();

        for (KeyValue keyValue : accessDataList) {
            String url = keyValue.getKey();
            Long count = keyValue.getValue();
            Optional<AccessDataItem> searchAny = searchEngineAccessDataItemList.stream().filter(e -> e.getFilter().equals(url)).findAny();
            Optional<AccessDataItem> linkAny = linkAccessDataItemList.stream().filter(e -> Objects.equals(e.getFilter(), url)).findAny();
            if (StringUtils.isEmpty(url)) {
                direct.setValue(direct.getValue() + count);
            } else if (url.contains(INTERNAL_KEY)) {
                localRedirect.setValue(localRedirect.getValue() + count);
            } else if (searchAny.isPresent()) {
                AccessDataItem accessDataItem = searchAny.get();
                accessDataItem.setValue(accessDataItem.getValue() + count);
            } else if (linkAny.isPresent()) {
                AccessDataItem accessDataItem = linkAny.get();
                accessDataItem.setValue(accessDataItem.getValue() + count);
            } else {
                other.setValue(other.getValue() + count);
            }
        }
        searchEngine.setValue(searchEngineAccessDataItemList.stream().mapToLong(e -> e.getValue()).sum());
        link.setValue(linkAccessDataItemList.stream().mapToLong(e -> e.getValue()).sum());

        AccessDataResult accessDataResult = new AccessDataResult();
        List<AccessData> innerResult = new ArrayList<>();
        innerResult.add(direct);
        innerResult.add(searchEngine);
        innerResult.add(link);
        innerResult.add(localRedirect);
        innerResult.add(other);
        accessDataResult.setInner(innerResult.stream().filter(e -> e.getValue() != 0).sorted(Comparator.comparing(AccessData::getValue).reversed()).collect(Collectors.toList()));

        List<AccessData> outResult = new ArrayList<>();
        linkAccessDataItemList.addAll(searchEngineAccessDataItemList);
        for (AccessDataItem accessDataItem : linkAccessDataItemList) {
            AccessData accessData = new AccessData();
            BeanUtils.copyProperties(accessDataItem, accessData);
            outResult.add(accessData);
        }
        accessDataResult.setOut(outResult.stream().filter(e -> e.getValue() != 0).sorted(Comparator.comparing(AccessData::getValue).reversed()).collect(Collectors.toList()));
        return accessDataResult.complete();
    }

    private List<AccessDataItem> getLinkAccessDataItemList() {
        List<Link> linkList = linkService.selectLinkList(new Link());
        return linkList.stream().map(e -> new AccessDataItem(e.getLinkName(), 0, getTopDomain(e.getUrl()))).collect(Collectors.toList());
    }

    private List<AccessDataItem> getSearchEngineAccessDataItemList() {
        List<AccessDataItem> accessDataItemList = new ArrayList<>();
        accessDataItemList.add(new AccessDataItem("谷歌", 0, "google.com"));
        accessDataItemList.add(new AccessDataItem("百度", 0, "baidu.com"));
        accessDataItemList.add(new AccessDataItem("搜狐", 0, "sohu.com"));
        accessDataItemList.add(new AccessDataItem("搜狗", 0, "sogou.com"));
        accessDataItemList.add(new AccessDataItem("360", 0, "360.com"));
        accessDataItemList.add(new AccessDataItem("必应", 0, "bing.com"));
        accessDataItemList.add(new AccessDataItem("雅虎", 0, "yahoo.com"));
        accessDataItemList.add(new AccessDataItem("Ask", 0, "ask.com"));
        accessDataItemList.add(new AccessDataItem("alo", 0, "alo.com"));
        return accessDataItemList;
    }


    /**
     * 获取访问折线图数据
     *
     * @return 折线图数据
     */
    private LineChartData getVisitorLineChartData() {
        //Get days before the current time mark one week.
        List<String> actualDataDayList = DateUtils.getPastDaysList(7);
        List<Long> actualData = new LinkedList<>();
        for (String day : actualDataDayList) {
            Long count = dashBoardMapper.getVisitorCountByCreateDate(day);
            actualData.add(count);
        }
        //Get days before the select time mark one week.
        List<String> expectedDataDayList = DateUtils.getPastDaysList(7, 7);
        List<Long> expectedData = new LinkedList<>();
        for (String day : expectedDataDayList) {
            Long count = dashBoardMapper.getVisitorCountByCreateDate(day);
            expectedData.add(count);
        }
        LineChartData<Long> lineChartData = new LineChartData<>();
        lineChartData.setActualData(actualData);
        lineChartData.setExpectedData(expectedData);
        lineChartData.setAxisData(actualDataDayList);
        return lineChartData;
    }

    private LineChartData getBlogLineChartData() {
        //Get days before the current time mark one week.
        List<String> actualDataDayList = DateUtils.getPastDaysList(7);
        List<Long> actualData = new LinkedList<>();
        for (String day : actualDataDayList) {
            Long count = dashBoardMapper.getBlogCountByCreateDate(day);
            actualData.add(count);
        }
        //Get days before the select time mark one week.
        List<String> expectedDataDayList = DateUtils.getPastDaysList(7, 7);
        List<Long> expectedData = new LinkedList<>();
        for (String day : expectedDataDayList) {
            Long count = dashBoardMapper.getBlogCountByCreateDate(day);
            expectedData.add(count);
        }
        LineChartData<Long> lineChartData = new LineChartData<>();
        lineChartData.setActualData(actualData);
        lineChartData.setExpectedData(expectedData);
        lineChartData.setAxisData(actualDataDayList);
        return lineChartData;
    }

}
