package top.maplefix.annotation;

import top.maplefix.config.datasource.DataSourceKey;

import java.lang.annotation.*;

/**
 * @author : Maple
 * @description : 数据源标识注解，默认为主数据源。该注解可直接在controller的方法上标注使用
 * @date : 2020/1/16 9:53
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    /**
     * 数据源，默认为主数据源
     */
    DataSourceKey value() default DataSourceKey.MASTER;
}
