package top.maplefix.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Maple
 * @description : 七牛云相关配置
 * @date : Created in 2019/8/8 16:01
 * @version : v1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuConfig {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String path;
}
