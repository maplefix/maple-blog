package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 友链实体
 * @date : Created in 2019/7/24 0:04
 * @editor:
 * @version: v2.1
 */
@Data
@Table(name = "t_links")
@NameStyle
public class Links implements Serializable {

    /**
     * 友链表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String linksId;
    /**
     * 友链名称
     */
    private String linksName;
    /**
     * 友链类型(友链类别 0-友链 1-推荐网站)
     */
    private String linksType;
    /**
     * 描述
     */
    private String description;
    /**
     * 头像地址
     */
    private String headerImg;
    /**
     * 链接地址
     */
    private String linksUrl;
    /**
     * 邮箱
     */
    private String linksEmail;
    /**
     * 是否显示(1:显示,0:不显示)
     */
    private String display;
    /**
     * 删除标识（1：删除，0：正常）
     */
    private String delFlag;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 排序值
     */
    private String  linksRank;

}
