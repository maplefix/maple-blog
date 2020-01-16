package top.maplefix.annotation;

import java.lang.annotation.*;

/**
 * @description : 自定义访问日志注解
 * @author : Maple
 * @date : Created in 2020/1/16 9:53
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VLog {
    /**
     * 请求的模块
     */
    String module() default "";

    String blogId() default "";
}
