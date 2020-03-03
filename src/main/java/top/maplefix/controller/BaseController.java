package top.maplefix.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import top.maplefix.constant.PageConstant;
import top.maplefix.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

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

    /**
     * 校验分页查询参数是否完整
     * @param params 参数map
     * @return
     */
    public boolean checkPageParam(Map<String, Object> params){
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        return !org.springframework.util.StringUtils.isEmpty(currPage) && !org.springframework.util.StringUtils.isEmpty(pageSize);
    }


}
