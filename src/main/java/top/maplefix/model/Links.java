package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 友链实体
 * @date : Created in 2019/7/24 0:04
 * @version : v1.0
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
    @Excel(name = "编号")
    private String linksId;
    /**
     * 友链名称
     */
    @Excel(name = "编号")
    private String linksName;
    /**
     * 友链类型(友链类别 0-友链 1-推荐网站)
     */
    @Excel(name = "友链类别",readConverterExp = "1=推荐网站,0=友链")
    private String linksType;
    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;
    /**
     * 头像地址
     */
    @Excel(name = "头像地址")
    private String headerImg;
    /**
     * 链接地址
     */
    @Excel(name = "链接地址")
    private String linksUrl;
    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String linksEmail;
    /**
     * 是否显示(1:显示,0:不显示)
     */
    @Excel(name = "是否显示",readConverterExp = "1=显示,0=不显示")
    private String display;
    /**
     * 删除标识（1：删除，0：正常）
     */
    @Excel(name = "删除标识",readConverterExp = "1=删除,0=正常")
    private String delFlag;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private String createDate;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    private String updateDate;
    /**
     * 排序值
     */
    @Excel(name = "排序值")
    private String  linksRank;

}
