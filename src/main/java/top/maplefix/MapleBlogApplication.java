package top.maplefix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author : Maple
 * @description : maple-blog starter
 *  由于多数据动态切换，需要排除DataSourceAutoConfiguration
 * @date : Created in 2019/7/23 23:38
 * @version : v1.0
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
        SpringApplication.run(MapleBlogApplication.class, args);
    }

}
