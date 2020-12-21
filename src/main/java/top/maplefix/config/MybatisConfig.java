package top.maplefix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.maplefix.interceptor.PerformanceInterceptor;


/**
 * @author wangjg
 * @description mybatis性能拦截
 * @date 2020/12/18 16:00
 */
@Configuration
public class MybatisConfig {

    /**
     * mybatis 自定义拦截器
     */
    @Bean
    public PerformanceInterceptor getInterceptor(){
        return new PerformanceInterceptor();
    }
}
