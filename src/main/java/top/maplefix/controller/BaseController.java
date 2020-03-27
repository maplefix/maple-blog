package top.maplefix.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import top.maplefix.common.BaseResult;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.SqlUtil;
import top.maplefix.utils.StringUtils;
import top.maplefix.vo.page.PageDomain;
import top.maplefix.vo.page.TableDataInfo;
import top.maplefix.vo.page.TableSupport;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author : Maple
 * @description :通用controller，所有controller类都继承它
 * @date : 2019/7/24 23:20
 */
@Slf4j
public class BaseController {

    @Autowired
    private MessageSource messageSource;

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }


    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageMethod.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     * @param list 列表数据
     * @return TableDataInfo
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.OK.value());
        rspData.setRows(list);
        if (list == null) {
            list = new ArrayList<>();
        }
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected BaseResult toResult(int rows) {
        return rows > 0 ? BaseResult.success() : BaseResult.error();
    }

    /**
     *获取国际化后的字符串
     *
     * @param interMark 国际化别名
     * @return returnMsg 返回的信息
     */
    public String internationalization(HttpServletRequest request,String interMark) {
        String returnMsg="";
        Locale locale = LocaleContextHolder.getLocale();
        //从请求头中获取国际化标识：lang=zh_CN/en_US
        String lang = request.getHeader("lang");
        //如果请求头有携带国际化标识，则根据标识国际化返回值
        try {
            if(!StringUtils.isEmpty(lang)) {
                String[] language = lang.split("-");
                locale = new Locale(language[0], language[1]);
            }
            returnMsg = messageSource.getMessage(interMark,null, locale);
        } catch (Exception e) {
            e.printStackTrace();
            returnMsg = "Unknown result";
        }
        return returnMsg;
    }

}
