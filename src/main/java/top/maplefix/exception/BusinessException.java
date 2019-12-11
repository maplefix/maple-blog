package top.maplefix.exception;

/**
 * @author : Maple
 * @description : 自定义业务异常
 * @Date : Created in 2019/3/31 20:55
 * @editor:
 * @version: v2.1
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
