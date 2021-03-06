package top.maplefix.exception;

/**
 * @author : Maple
 * @description : 自定义业务异常
 * @date : 2019/3/31 20:55
 * @version : v1.0
 */
public class BusinessException extends RuntimeException{

    protected final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
