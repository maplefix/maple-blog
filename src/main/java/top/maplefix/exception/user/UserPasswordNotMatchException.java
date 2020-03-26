package top.maplefix.exception.user;

/**
 * @author Maple
 * @description 用户密码异常
 * @date 2020/2/2 18:36
 */
public class UserPasswordNotMatchException extends UserException {

    public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
