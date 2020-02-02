package top.maplefix.exception.user;

/**
 * @author wangjg
 * @description 验证码异常
 * @date 2020/2/2 18:35
 */
public class CaptchaException extends UserException {

    public CaptchaException() {
        super("user.captcha.error", null);
    }
}
