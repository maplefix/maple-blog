package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Maple
 * @description 角色实体类
 * @date 2020/1/15 15:55
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "t_role")
public class Role implements Serializable {

    /**
     * 主键id
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String roleId;
    /**
     * 角色名称
     */
    @Excel(name = "角色名称")
    private String roleName;
    /**
     * 角色描述
     */
    @Excel(name = "橘色描述")
    private String description;
    /**
     * 角色类型(0:系统角色,1:自定义角色)
     */
    @Excel(name = "角色类型",readConverterExp = "1=自定义角色,0=系统角色")
    private Integer roleType;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private String createData;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    private String updateData;
}
