package top.maplefix.redis;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author Maple
 * @description redis缓存过期注解
 * @date 2020/1/18 15:46
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheExpire {
    /**
     * expire time, default 60s
     */
    @AliasFor("expire")
    long value() default 60L;

    /**
     * expire time, default 60s
     */
    @AliasFor("value")
    long expire() default 60L;

    /**
     * 时间单位
     *
     * @return
     */
    TimeType type() default TimeType.SECONDS;
}
