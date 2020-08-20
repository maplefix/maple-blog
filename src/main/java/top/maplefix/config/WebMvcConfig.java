package top.maplefix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * @author : Maple
 * @description : 自定义web拦截器,资源文件管理配置
 * @date : 2020/1/16 9:57
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String pathUtl = Paths.get(MapleBlogConfig.getProfile()).normalize().toUri().toASCIIString();
        registry.addResourceHandler("/file/**").addResourceLocations(pathUtl).setCachePeriod(0);

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
