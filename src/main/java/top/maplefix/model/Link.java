package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 友链实体
 * @date : Created in 2020/1/15 14：51
 */
@Data
@Table(name = "t_link")
public class Link implements Serializable {

    /**
     * 友链表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String linkId;
    /**
     * 友链名称
     */
    @Excel(name = "编号")
    private String linkName;
    /**
     * 友链类型(友链类别 0-友链 1-推荐网站)
     */
    @Excel(name = "友链类别",readConverterExp = "1=推荐网站,0=友链")
    private Integer linkType;
    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;
    /**
     * 头像地址
     */
    @Excel(name = "头像地址")
    private String avatar;
    /**
     * 链接地址
     */
    @Excel(name = "链接地址")
    private String url;
    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;
    /**
     * 是否显示(1:显示,0:不显示)
     */
    @Excel(name = "是否显示",readConverterExp = "1=显示,0=不显示")
    private Integer display;
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
    private String  seq;

}
