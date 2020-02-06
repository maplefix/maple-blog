package top.maplefix.utils.inter;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * @author Maple
 * @description 自定义国际化解析器
 * @date 2020/1/16 15:42
 */
public class CustomLocaleResolver implements LocaleResolver {

    private static final String LANG = "language";
    private static final String LANG_SESSION = "language_session";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getHeader(LANG);
        Locale locale = null;
        if (lang != null && lang != "") {
            String[] langueage = lang.split("_");
            locale = new Locale(langueage[0], langueage[1]);

            HttpSession session = request.getSession();
            session.setAttribute(LANG_SESSION, locale);
        } else {
            HttpSession session = request.getSession();
            Locale localeInSession = (Locale) session.getAttribute(LANG_SESSION);
            if (localeInSession != null) {
                locale = localeInSession;
            }else{
                locale = Locale.US;
            }
        }
        return locale;

    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
