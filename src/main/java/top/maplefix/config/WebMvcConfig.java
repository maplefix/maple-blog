package top.maplefix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.maplefix.interceptor.LoginInterceptor;

/**
 * @author : Maple
 * @description : 自定义web拦截器
 * @date : Created in 2020/1/16 9:57
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //所有已api开头的访问都要进入RedisSessionInterceptor拦截器进行登录验证，并排除login接口(全路径)。必须写成链式，分别设置的话会创建多个拦截器。
        //必须写成getSessionInterceptor()，否则SessionInterceptor中的@Autowired会无效
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/api/**")
                .excludePathPatterns("/api/admin/captcha").excludePathPatterns("/api/admin/login")
                .excludePathPatterns("/admin/dist/**").excludePathPatterns("/admin/plugins/**")
                .excludePathPatterns("/blog/default/**").excludePathPatterns("/blog/plugins/**")
                .excludePathPatterns("/druid/**");
    }

}
