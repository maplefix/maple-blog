package top.maplefix.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Maple
 * @description : 博客项目相关配置
 * @date : 2020/1/16 9:57
 */
@Component
@ConfigurationProperties(prefix = "maple")
public class MapleBlogConfig {
    /**
     * 项目名称
     */
    public String name;
    /**
     * 版本
     */
    private String version;
    /**
     * 版权年份
     */
    private String copyrightYear;
    /**
     * 上传路径
     */
    private static String profile;
    /**
     * 日志存放路径
     */
    private String logPath;
    /**
     * 是否获取id地址开关
     */
    private static boolean addressEnabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        MapleBlogConfig.profile = profile;
    }

    public  String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        MapleBlogConfig.addressEnabled = addressEnabled;
    }
}
