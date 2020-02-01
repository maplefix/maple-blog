package top.maplefix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;
import top.maplefix.utils.SpringUtils;

/**
 * @author : Maple
 * @description : maple-blog starter
 *  由于多数据动态切换，需要排除DataSourceAutoConfiguration
 * @date : 2020/1/16 17:05
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan( basePackages = {"top.maplefix.mapper"})
public class MapleBlogApplication {
    /**
     * 解决bean转换时null值报错
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MapleBlogApplication.class, args);
        //启动时设置spring上下文对象到SpringUtils，也可以自定义bean实现implements ApplicationContextAware来设置上下文对象
        SpringUtils.setApplicationContext(applicationContext);
    }

}
