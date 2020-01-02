package top.maplefix.annotation;


import top.maplefix.enums.OperationType;

import java.lang.annotation.*;

/**
 * @author : Maple
 * @description : 自定义操作日志注解
 * @date : Created in 2019/7/27 14:43
 * @version : v2.1
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OLog {
    /**
     * 模块
     */
    String module() default "";

    /**
     * 操作类型，默认其他
     */
    OperationType businessType() default OperationType.OTHER;


    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;
}
