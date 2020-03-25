package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 博客标签实体
 * @date : 2020/1/15 14：51
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Tag extends BaseEntity implements Serializable {

    /**
     * 标签表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    public String tagId;
    /**
     * 标签名
     */
    @Excel(name = "标签名")
    public String tagName;
    /**
     * 创建时间
     */
    @Excel(name = "标签轮廓颜色")
    public String color;

    /**
     * 标签类型，1博客标签，2读书标签
     */
    @Excel(name = "标签类型")
    public Integer type;
    /**
     * 备注
     */
    @Excel(name = "备注")
    public String remark;

    /**
     * 标签总数
     * @Transient 注解表明该字段不与数据库字段相对应
     */
    @Transient
    public Integer count;

    /**
     * 该标签关联博客数
     */
    @Transient
    public Integer blogCount;

    public Tag(String tagName, String color, Integer type) {
        this.color = color;
        this.tagName = tagName;
        this.type = type;
    }
}
