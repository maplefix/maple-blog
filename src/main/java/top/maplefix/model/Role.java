package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Maple
 * @description 角色实体类
 * @date 2020/1/15 15:55
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends BaseEntity implements Serializable {

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
     * 角色权限字符串
     */
    private String roleKey;
    /**
     * 角色描述
     */
    @Excel(name = "角色描述")
    private String description;
    /**
     * 角色类型(0:系统角色,1:自定义角色)
     */
    @Excel(name = "角色类型",readConverterExp = "1=自定义角色,0=系统角色")
    private Integer roleType;
    /**
     * 角色类型(0:系统角色,1:自定义角色)
     */
    @Excel(name = "角色状态",readConverterExp = "1=正常,0=停用")
    private Integer status;
    /**
     * 排序号
     */
    @Excel(name = "排序")
    private Integer seq;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    private boolean flag = false;

    /**
     * 菜单组
     */
    private String[] menuIds;


    public Role(String roleId) {
        this.roleId = roleId;
    }

    public static boolean isAdmin(String roleId) {
        return "admin".equals(roleId);
    }

    public boolean isAdmin() {
        return isAdmin(this.roleId);
    }
}
