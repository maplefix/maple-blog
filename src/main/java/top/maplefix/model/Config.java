package top.maplefix.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Maple
 * @description 参数配置表
 * @date 2020/4/17 14:44
 */
@Data
@NoArgsConstructor
public class Config extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 参数名称
     */
    private String configName;
    /**
     * 是否系统内置，1是 0否
     */
    private String configType;
    /**
     * 参数key
     */
    private String configKey;

    /**
     * 参数value
     */
    private String configValue;
    /**
     * 备注信息
     */
    private String remark;
}
