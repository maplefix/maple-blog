package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 博客标签实体
 * @date : 2020/1/15 14：51
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "t_label")
@NoArgsConstructor
public class Label implements Serializable {

    /**
     * 标签表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    public String labelId;
    /**
     * 标签名
     */
    @Excel(name = "标签名")
    public String labelName;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    public String createDate;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    public String updateDate;
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
}
