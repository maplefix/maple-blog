package top.maplefix.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Maple
 * @description : 七牛云相关配置
 * @date : 2020/1/16 9:57
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuConfig {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String path;
}
