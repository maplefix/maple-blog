package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.maplefix.annotation.Excel;

import java.io.Serializable;

/**
 * @author Maple
 * @description 角色实体类
 * @date 2020/1/15 15:55
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends BaseEntity implements Serializable {

    /**
     * 主键id
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 角色名称
     */
    @Excel(name = "角色名称")
    private String roleName;
    /**
     * 角色权限字符串
     */
    @Excel(name = "角色权限字符串")
    private String roleKey;
    /**
     * 角色描述
     */
    @Excel(name = "角色描述")
    private String remark;

    /**
     * 角色状态
     */
    @Excel(name = "角色状态",readConverterExp = "0=正常,1=停用")
    private String status;
    /**
     * 排序号
     */
    @Excel(name = "排序")
    private String roleSort;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    private boolean flag = false;

    /**
     * 菜单组
     */
    private Long[] menuIds;


    public Role(Long roleId) {
        this.id = roleId;
    }

    private static boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }

    public boolean isAdmin() {
        return isAdmin(this.id);
    }
}
