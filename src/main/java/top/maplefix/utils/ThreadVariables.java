package top.maplefix.utils;

/**
 * @author Maple
 * @description threadLocal常量
 * @date 2020/1/16 15:20
 */
public class ThreadVariables {

    private static ThreadLocal<String> language;
    private static final String DEF_LANGUAGE = "en_US";

    private static ThreadLocal<String> user;

    public static void setLanguage(String language) {
        if (ThreadVariables.language == null) {
            ThreadVariables.language = new ThreadLocal<>();
        }
        if (StringUtils.isEmpty(language)) {
            ThreadVariables.language.set(DEF_LANGUAGE);
        } else {
            ThreadVariables.language.set(language);
        }
    }

    public static String getLanguage() {
        if (ThreadVariables.language == null || ThreadVariables.language.get() == null) {
            return DEF_LANGUAGE;
        }
        return language.get();
    }

    public static void removeLanguage() {
        if (language != null) {
            language.remove();
        }
    }


    public static void setUser(String user) {
        if (ThreadVariables.user == null) {
            ThreadVariables.user = new ThreadLocal<>();
        }
        if (StringUtils.isEmpty(user)) {
            ThreadVariables.user.set(null);
        } else {
            ThreadVariables.user.set(user);
        }
    }

    public static String getUser() {
        if (ThreadVariables.user == null || ThreadVariables.user.get() == null) {
            return null;
        }
        return user.get();
    }

    public static void removeUser() {
        if (user != null) {
            user.remove();
        }
    }

}
