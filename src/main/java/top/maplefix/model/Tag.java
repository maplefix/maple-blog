package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.maplefix.annotation.Excel;

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
    @Excel(name = "主键")
    private Long id;
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

    public Tag(String tagName, String color) {
        this.color = color;
        this.tagName = tagName;
    }
}
