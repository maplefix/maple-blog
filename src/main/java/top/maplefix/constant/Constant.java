package top.maplefix.constant;

/**
 * @author : Maple
 * @description : 系统通用常量
 * @date : 2020/1/15 16:31
 */
public class Constant {
    private Constant(){}
    /**
     * 登陆用户的 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";
    /**
     * 验证码key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";
    /**
     * 验证码过期时间(单位为分钟)
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * token
     */
    public static final String TOKEN = "token";

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * the prefix of token
     */
    public static final String LOGIN_USER_KEY = "login_user_key";
    /**
     * 登陆用户信息
     */
    public static final String LOGIN_USER = "LOGIN_USER";
    /**
     * session中保存的登陆用户id
     */
    public static final String USER_TOKEN = "USER_TOKEN";
    /**
     * 请求成功返回值
     */
    public static final String SUCCESS_MSG = "success";
    /**
     * 请求失败返回值
     */
    public static final String FAIL_MSG = "fail";

    /**
     * math验证码
     */
    public static String math = "math";
    /**
     * charC 字母验证码
     */
    public static String charC = "char";


    /**
     * common constant for success
     */
    public static final int SUCCESS = 1;
    /**
     * common constant for failed
     */
    public static final int FAILED = 0;

    /**
     * 显示
     */
    public static final int DISPLAY = 1;
    /**
     * 隐藏
     */
    public static final int HIDE = 0;

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * IE 浏览器
     */
    public static final String MSIE = "MSIE";
    /**
     * 火狐浏览器
     */
    public static final String FIREFOX = "Firefox";
    /**
     * 谷歌浏览器
     */
    public static final String CHROME = "Chrome";

    /**
     * .
     */
    public static final String POINT = ".";

    /**
     * unknown
     */
    public static final String UNKNOWN = "unknown";

    /**
     * Integer true
     */
    public static final Integer  TRUE = 1;
    /**
     * Integer false
     */
    public static final Integer FALSE = 0;

    /**
     * 校验返回结果码
     */
    public final static String UNIQUE = "0";

    public final static String NOT_UNIQUE = "1";
}
