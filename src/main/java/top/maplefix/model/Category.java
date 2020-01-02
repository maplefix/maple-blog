package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 博客分类实体
 * @date : Created in 2019/7/24 0:04
           Edited in 2019/10/30 14：24
 * @version : v2.1
 */
@Data
@Table(name = "t_category")
@NameStyle
public class Category implements Serializable {

    /**
     * 分类主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String categoryId;
    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String categoryName;
    /**
     * 分类图标相对地址
     */
    @Excel(name = "图标地址")
    private String categoryIcon;
    /**
     * 权重
     */
    @Excel(name = "权重")
    private Integer height;
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
     * 描述信息
     */
    @Excel(name = "描述")
    private String description;

    /**
     * 关联博客数
     * @Transient注解表明该字段不与数据库字段相对应
     */
    @Transient
    @Excel(name = "关联博客数")
    public Integer count;

}
