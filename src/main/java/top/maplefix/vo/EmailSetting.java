package top.maplefix.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Maple
 * @description 邮件设置类
 * @date 2020/1/22 20:20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailSetting {

    /**
     * 邮件服务器SMTP地址
     */
    private String host;

    /**
     * 邮件服务器 SMTP 端口
     */
    private Integer port;

    /**
     * 发件者用户名，默认为发件人邮箱前缀
     */
    private String user;

    /**
     * 密码或者授权码
     */
    private String password;

    /**
     * 发件人
     */
    private String fromEmail;
    /**
     * 站长邮箱
     */
    private String stationmasterEmail;
}
