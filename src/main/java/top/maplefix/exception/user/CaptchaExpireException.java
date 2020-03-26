package top.maplefix.exception.user;

/**
 * @author Maple
 * @description 验证码过期异常
 * @date 2020/2/2 18:35
 */
public class CaptchaExpireException extends UserException {

    public CaptchaExpireException() {
        super("user.captcha.expire", null);
    }
}

