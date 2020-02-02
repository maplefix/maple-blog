package top.maplefix.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Maple
 * @description 站点设置类
 * @date 2020/1/22 20:26
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteSetting {
    /**
     * the title of front site
     */
    private String title;
    /**
     * icp of this site
     */
    private String icp;
    /**
     * description of the site
     */
    private String description;
    /**
     * copyright of this site
     */
    private String copyright;
    /**
     * description of copyright
     */
    private String copyrightDesc;
    /**
     * description of copyright with english
     */
    private String copyrightDescEn;
}
