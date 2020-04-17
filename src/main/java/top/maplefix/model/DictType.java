package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;

/**
 * @author wangjg
 * @description 字典类型实体类
 * @date 2020/4/17 9:36
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictType {
    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String dataTypeId;
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
}