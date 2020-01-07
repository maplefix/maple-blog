package top.maplefix.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import top.maplefix.annotation.TargetDataSource;
import top.maplefix.config.datasource.DynamicDataSourceContextHolder;

/**
 * @author : Maple
 * @description : 动态切换数据源aop
 * @date : Created in 2019/11/20 14:43
 * @version : v1.0
 */
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {

    /**
     * 以自定义注解注解在的方法为切点，动态切换数据源
     * @param joinPoint 切点
     * @param targetDataSource 数据源
     */
    @Before("@annotation(targetDataSource))")
    public void switchDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource){
        if ( !DynamicDataSourceContextHolder.containDataSourceKey( targetDataSource.value().getName() ) ) {
            log.error("DataSource [{}] doesn't exist, use default DataSource [{}]", targetDataSource.value().getName(),targetDataSource.value());
        } else {
            DynamicDataSourceContextHolder.setDataSourceKey( targetDataSource.value().getName() );
            log.info("Switch DataSource to [{}] in Method [{}]",
                    DynamicDataSourceContextHolder.getDataSourceKey(), joinPoint.getSignature());
        }
    }

    @After("@annotation(targetDataSource))")
    public void restoreDataSource(JoinPoint joinPoint,TargetDataSource targetDataSource){
        DynamicDataSourceContextHolder.clearDataSourceKey();
        log.info("Restore DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), joinPoint.getSignature());
    }

}
