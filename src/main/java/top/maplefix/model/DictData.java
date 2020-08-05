package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.maplefix.annotation.Excel;

import java.io.Serializable;

/**
 * @author : Maple
 * @description : 字典数据表
 * @date : 2020/1/15 14:40
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class DictData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7458813809933292186L;
    /**
     * 字典表主键
     */
    @Excel(name = "编号")
    private Long dictCode;
    /**
     * 字典标签
     */
    @Excel(name = "字典标签")
    private String dictLabel;
    /**
     * 字典类型
     */
    @Excel(name = "字典类型")
    private String dictType;
    /**
     * 字典值
     */
    @Excel(name = "字典值")
    private String dictValue;
    /**
     * 样式属性（其他样式扩展）
     */
    @Excel(name = "样式属性")
    private String cssClass;

    /**
     * 表格字典样式
     */
    @Excel(name = "表格字典样式")
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    @Excel(name = "是否默认")
    private String isDefault;
    /**
     * 排序号
     */
    @Excel(name = "排序号")
    private Integer dictSort;
    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "字典状态")
    private String status;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
}
