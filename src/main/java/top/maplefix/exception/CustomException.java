package top.maplefix.exception;

import org.springframework.http.HttpStatus;
/**
 * @author : Maple
 * @description : 自定义异常
 * @date : 2020/1/18 15:21
 */
public class CustomException extends RuntimeException {

    private HttpStatus code;

    private String message;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message, HttpStatus code) {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getCode() {
        return code;
    }
}
