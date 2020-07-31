package top.maplefix.model;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;
    /**
     * 备注信息
     */
    private String remark;
}
