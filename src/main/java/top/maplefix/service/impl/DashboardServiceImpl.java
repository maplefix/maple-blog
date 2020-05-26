package top.maplefix.service.impl;

import lombok.extern.slf4j.Slf4j;
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
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.vo.LineChartData;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Maple
 * @description : dashboard面板service实现类
 * @date : 2020/2/15 18:08
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;
    @Autowired
    private DictDataService dictDataService;

    @Override
    public Map<String, Long> getPanelGroupData() {
        Map<String, Long> resultMap = new HashMap<>();
        Long visitorCount = dashboardMapper.getVisitorCount();
        Long blogCount = dashboardMapper.getBlogCount();
        Long bookCount = dashboardMapper.getBookCount();
        Long noteCount = dashboardMapper.getNoteCount();
        resultMap.put("visitorCount", visitorCount);
        resultMap.put("blogCount", blogCount);
        resultMap.put("bookCount", bookCount);
        resultMap.put("noteCount", noteCount);
        return resultMap;
    }

    @Override
    @Cacheable(value = "DashBoard", key = "'LineChartData'+ #type")
    @CacheExpire(expire = 3, type = TimeType.HOURS)
    public LineChartData<Long> getLineChartData(String type) {
        LineChartData lineChartData = null;
        switch (type) {
            case LineChartData.BLOG_LINE:
                lineChartData = getBlogLineChartData();
                break;
            case LineChartData.BOOK_LINE:
                lineChartData = getBookLineChartData();
                break;
            case LineChartData.NOTE_LINE:
                lineChartData = getNoteLineChartData();
                break;
            case LineChartData.VISITOR_LINE:
                lineChartData = getVisitorLineChartData();
                break;
            default:
        }
        log.info("get line chart data \n{}", lineChartData);
        return lineChartData;
    }

    @Override
    @Cacheable(value = "DashBoard", key = "'SpiderData'")
    public List<Map<String, Long>> getSpiderData() {
        return dashboardMapper.getSpiderData();
    }

    @Override
    public List<String> getVisitLogStringList() {
        List<VisitLog> visitLogList = dashboardMapper.getVisitLog();
        List<String> result = new ArrayList<>();
        for (VisitLog visitLog : visitLogList) {
            String name = "";
            if (visitLog.getPageId() != null) {
                if (visitLog.getPageId() == "-1000") {
                    name = "留言页";
                } else {
                    String pageId = visitLog.getPageId();
                    if (pageId != null) {
                        name = dashboardMapper.getBlogNameByPageId(pageId);
                    }
                }
            }
            //XXX 分钟前 : @47.112.14.207 浏览了 <strong>title</strong> <a href='url'>XXXXX</a>
            result.add(StringUtils.format("{} : @{} 浏览了 <strong> {} </strong> <a href='{}'> {} </a>",
                    DateUtils.showTime(visitLog.getCreateDate()), visitLog.getIp(), visitLog.getModule(),
                    visitLog.getUrl(), StringUtils.isEmpty(name) ? visitLog.getUrl() : name));
        }
        return result;
    }

    @Override
    public List<String> getLoginLogStringList() {
        List<LoginLog> loginLogList = dashboardMapper.getLoginLogList();
        List<String> result = new LinkedList<>();
        for (LoginLog loginLog : loginLogList) {
//            XXX 分钟前: XXXX 登录系统 XXXX
            result.add(StringUtils.format("{} : {} 登录系统 {}{}", DateUtils.showTime(loginLog.getCreateDate()),
                    loginLog.getLoginName(), loginLog.getStatus().equals(Constant.SUCCESS) ? "成功" : "失败",
                    loginLog.getStatus().equals(Constant.SUCCESS) ? "" : "异常信息:" + loginLog.getLoginMsg()));
        }
        return result;
    }

    private Map<String,String> getOperateTypeString() {
        List<DictData> operateTypeList = dictDataService.selectDictDataByType("sys_oper_type");
        return operateTypeList.stream().collect(Collectors.toMap(DictData::getDictValue, DictData::getDictLabel));
    }
    @Override
    public List<String> getOperateLogStringList() {
        List<OperateLog> operateLogList = dashboardMapper.getOperateLogList();
        List<String> result = new LinkedList<>();
        Map<String, String> operateTypeString = getOperateTypeString();
        for (OperateLog operateLog : operateLogList) {
            result.add(StringUtils.format("{}:{}对{}进行{}操作{}{}", DateUtils.showTime(operateLog.getCreateDate()),
                    operateLog.getOperateName(), operateLog.getModule(), operateLog.getFunction(),
                    operateLog.getStatus().equals(Constant.SUCCESS) ? "成功" : "失败",
                    operateLog.getStatus().equals(Constant.SUCCESS) ? "" : ",异常信息:" + operateLog.getExceptionMsg()));
            String  businessType = operateLog.getFunction();
            String typeString = operateTypeString.get(businessType);
            result.add(StringUtils.format("{} : {} 对 {} 进行 {} 操作 {} {}", DateUtils.showTime(operateLog.getCreateDate()),
                    operateLog.getOperateName(), operateLog.getModule(), typeString, operateLog.getStatus().equals(Constant.SUCCESS) ? "成功" : "失败",
                    operateLog.getStatus().equals(Constant.SUCCESS) ? "" : ",异常信息:" + operateLog.getExceptionMsg()));
        }
        return result;
    }

    @Override
    public List<String> getTaskLogStringList() {
        List<String> result = new LinkedList<>();
        List<JobLog> jobLogList = dashboardMapper.getJobLogList();
        for (JobLog jobLog : jobLogList) {
            result.add(StringUtils.format("{} : {} 执行 {}", DateUtils.showTime(jobLog.getCreateDate()),
                    jobLog.getJobName(), jobLog.getStatus().equals(Constant.SUCCESS) ? "成功" : "失败"));
        }
        return result;
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
            Long count = dashboardMapper.getVisitorCountByCreateDate(day);
            actualData.add(count);
        }
        //Get days before the select time mark one week.
        List<String> expectedDataDayList = DateUtils.getPastDaysList(7, 7);
        List<Long> expectedData = new LinkedList<>();
        for (String day : expectedDataDayList) {
            Long count = dashboardMapper.getVisitorCountByCreateDate(day);
            expectedData.add(count);
        }
        LineChartData<Long> lineChartData = new LineChartData<>();
        lineChartData.setActualData(actualData);
        lineChartData.setExpectedData(expectedData);
        lineChartData.setAxisData(actualDataDayList);
        return lineChartData;
    }

    private LineChartData getNoteLineChartData() {
        //Get days before the current time mark one week.
        List<String> actualDataDayList = DateUtils.getPastDaysList(7);
        List<Long> actualData = new LinkedList<>();
        for (String day : actualDataDayList) {
            Long count = dashboardMapper.getNoteCountByCreateDate(day);
            actualData.add(count);
        }
        //Get days before the select time mark one week.
        List<String> expectedDataDayList = DateUtils.getPastDaysList(7, 7);
        List<Long> expectedData = new LinkedList<>();
        for (String day : expectedDataDayList) {
            Long count = dashboardMapper.getNoteCountByCreateDate(day);
            expectedData.add(count);
        }
        LineChartData<Long> lineChartData = new LineChartData<>();
        lineChartData.setActualData(actualData);
        lineChartData.setExpectedData(expectedData);
        lineChartData.setAxisData(actualDataDayList);
        return lineChartData;
    }

    private LineChartData getBookLineChartData() {
        //Get days before the current time mark one week.
        List<String> actualDataDayList = DateUtils.getPastDaysList(7);
        List<Long> actualData = new LinkedList<>();
        for (String day : actualDataDayList) {
            Long count = dashboardMapper.getBookCountByCreateDate(day);
            actualData.add(count);
        }
        //Get days before the select time mark one week.
        List<String> expectedDataDayList = DateUtils.getPastDaysList(7, 7);
        List<Long> expectedData = new LinkedList<>();
        for (String day : expectedDataDayList) {
            Long count = dashboardMapper.getBookCountByCreateDate(day);
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
            Long count = dashboardMapper.getBlogCountByCreateDate(day);
            actualData.add(count);
        }
        //Get days before the select time mark one week.
        List<String> expectedDataDayList = DateUtils.getPastDaysList(7, 7);
        List<Long> expectedData = new LinkedList<>();
        for (String day : expectedDataDayList) {
            Long count = dashboardMapper.getBlogCountByCreateDate(day);
            expectedData.add(count);
        }
        LineChartData<Long> lineChartData = new LineChartData<>();
        lineChartData.setActualData(actualData);
        lineChartData.setExpectedData(expectedData);
        lineChartData.setAxisData(actualDataDayList);
        return lineChartData;
    }

}
