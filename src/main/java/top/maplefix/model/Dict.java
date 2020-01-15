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
 * @description : 博客字典实体
 * @date : Created in 2020/1/15 14:40
 */
@Data
@Table(name = "t_dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = -7458813809933292186L;
    /**
     * 字典表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String dictId;
    /**
     * 关键字
     */
    @Excel(name = "关键字")
    private String keyword;
    /**
     * 字典名
     */
    @Excel(name = "字典名")
    private String dictName;
    /**
     * 字典值
     */
    @Excel(name = "字典值")
    private String dictValue;
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
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 排序号
     */
    @Excel(name = "排序号")
    private Integer seq;
}
