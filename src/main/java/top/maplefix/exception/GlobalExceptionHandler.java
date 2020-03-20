package top.maplefix.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.maplefix.common.BaseResult;
import top.maplefix.common.ResultCode;
import top.maplefix.utils.StringUtils;

import java.util.Objects;

/**
 * @author Maple
 * @description 全局异常处理
 * @date 2020/3/12 17:22
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public BaseResult baseException(BaseException e) {
        return BaseResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public BaseResult businessException(CustomException e) {
        if (StringUtils.isNull(e.getCode())) {
            return BaseResult.error(e.getMessage());
        }
        return BaseResult.error(e.getCode().hashCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResult handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResult.error(ResultCode.FORBIDDEN, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public BaseResult handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return BaseResult.error(ResultCode.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public BaseResult handleAccountExpiredException(AccountExpiredException e) {
        log.error(e.getMessage(), e);
        return BaseResult.error(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public BaseResult handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage(), e);
        return BaseResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResult.error(e.getMessage());
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        log.error(e.getMessage(), e);
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        if ("不能为空".equals(message)) {
            message = str[1] + ":" + message;
        }
        return BaseResult.error(message);
    }
}
