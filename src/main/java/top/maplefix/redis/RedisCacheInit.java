package top.maplefix.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.maplefix.config.QiNiuConfig;

/**
 * @author Maple
 * @description redis初始化加载信息
 * @date 2020/1/18 14:37
 */
//@Component
@Slf4j
public class RedisCacheInit implements CommandLineRunner {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        QiNiuConfig qiNiuConfig = new QiNiuConfig();
        if(qiNiuConfig != null){
            redisTemplate.delete("qn");
            redisTemplate.opsForValue().set("qn",qiNiuConfig.toString());
            log.info("redis初始化信息成功:{}",redisTemplate.opsForValue().get("qn"));
        }
    }
}
