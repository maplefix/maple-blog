package top.maplefix.utils.inter;

import top.maplefix.utils.SpringUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.ThreadVariables;

import java.util.Locale;

/**
 * @author Maple
 * @description 自定义I18n
 * @date 2020/1/16 15:08
 */
public class I18n {

    /**
     * 获取国际化值
     * @param locale 方言
     * @param key 要获取的国际化值的key
     * @return
     */
    public static String getValue(Locale locale, String key) {
        if (locale == null) {
            locale = new Locale(ThreadVariables.getLanguage());
        }
        return SpringUtils.getApplicationContext().getMessage(key, null, locale);
    }

    /**
     * 根据key获取国际化的值
     * 从当前ThreadLocal中获取语言
     * @param key 要获取的国际化值的key
     * @return
     */
    public static String getValue(String key) {
        if (ThreadVariables.getLanguage().toLowerCase().contains("zh")) {
            return getValue(Locale.SIMPLIFIED_CHINESE, key);
        } else {
            return getValue(Locale.US, key);
        }
    }


    /**
     * 根据key和语言获取国际化内容
     * @param language 语言，如：zh_cn、en_us等
     * @param key 国际化的key
     * @return
     */
    public static String getValue(String language, String key){
        if(StringUtils.isNotBlank(language) && language.toLowerCase().contains("zh")){
            return getValue(Locale.SIMPLIFIED_CHINESE, key);
        }else{
            return getValue(Locale.US, key);
        }
    }


}
