package top.maplefix.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Maple
 * @description : 项目相关配置
 * @date : Created in 2019/8/1 20：09
 * @version : v2.1
 */
@Component
@ConfigurationProperties(prefix = "maple")
public class SystemConfig {
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
        SystemConfig.profile = profile;
    }


    public static String getAvatarPath() {
        return profile + "avatar/";
    }

    public static String getDownloadPath() {
        return profile + "download/";
    }

    public static String getUploadPath() {
        return profile + "upload/";
    }

    public static String getImagePath() {
        return profile + "images/";
    }
}
