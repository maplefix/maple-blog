package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 博客分类实体
 * @date : Created in 2020/1/15 14:39
 */
@Data
@Table(name = "t_category")
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
     * @Transient 注解表明该字段不与数据库字段相对应
     */
    @Transient
    public Integer count;

}
