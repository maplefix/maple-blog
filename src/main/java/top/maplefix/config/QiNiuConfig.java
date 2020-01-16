package top.maplefix.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Maple
 * @description : 七牛云相关配置
 * @date : Created in 2020/1/16 9:57
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
