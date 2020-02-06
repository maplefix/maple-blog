package top.maplefix.utils.inter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import top.maplefix.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author Maple
 * @description 国际化转化工具类
 * @date 2020/1/16 15:47
 */
@Slf4j
public class InterUtils {

    public static String defLanguage = "en";

    private static final String ZH = "zh";
    /**
     * 获取国际化标签
     * @param interMark 国家化别名
     * @return returnMsg 返回的信息
     */
    public static String interInfo(HttpServletRequest request, MessageSource messageSource, String interMark) {
        String returnMsg="";
        try {
            String language = request.getHeader("language");
            if(StringUtils.isEmpty(language)){
                language = InterUtils.defLanguage;
            }
            if(language.toLowerCase().contains(ZH)){
                returnMsg = messageSource.getMessage(interMark, null, Locale.SIMPLIFIED_CHINESE);
            }else{
                returnMsg = messageSource.getMessage(interMark, null, Locale.US);
            }
        } catch (Exception e) {
            returnMsg = interMark;
        }
        return returnMsg;
    }

    public static String interInfo(String language, MessageSource messageSource, String interMark) {
        String returnMsg="";
        try {
            if(StringUtils.isEmpty(language)){
                language = InterUtils.defLanguage;
            }
            if(language.toLowerCase().contains(ZH)){
                returnMsg = messageSource.getMessage(interMark, null, Locale.SIMPLIFIED_CHINESE);
            }else{
                returnMsg = messageSource.getMessage(interMark, null, Locale.US);
            }
        } catch (Exception e) {
            returnMsg = interMark;
        }
        return returnMsg;
    }

    /**
     * 是否为中文
     * @param request
     * @return
     */
    public static boolean isZh(HttpServletRequest request){
        String language = request.getHeader("language");
        if(StringUtils.isEmpty(language)){
            language = InterUtils.defLanguage;
        }
        if(language.toLowerCase().contains(ZH)){
            return true;
        }
        return false;
    }

}
