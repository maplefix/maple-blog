package top.maplefix.exception.user;

import top.maplefix.exception.BaseException;

/**
 * @author wangjg
 * @description 用户自定义异常
 * @date 2020/2/2 18:34
 */
public class UserException extends BaseException {

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
