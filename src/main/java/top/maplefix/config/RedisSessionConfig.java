package top.maplefix.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author : Maple
 * @description : redis存储session配置
 *  将session信息保存到redis中，spring中的session不在生效。设置超时时间为20分钟
 * @date : 2020/1/16 10:18
 */
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*60)
@Configuration
public class RedisSessionConfig {
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        //让springSession不再执行config命令
        return ConfigureRedisAction.NO_OP;
    }
}
