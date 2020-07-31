package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.maplefix.annotation.Excel;

import java.io.Serializable;

/**
 * @author Maple
 * @description 字典类型实体类
 * @date 2020/4/17 9:36
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class DictType extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 字典名
     */
    private String dictName;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 状态（0停用，1正常）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
}
