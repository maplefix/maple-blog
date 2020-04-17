package top.maplefix.secrrity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.maplefix.model.SysUser;
import top.maplefix.service.MenuService;
import top.maplefix.service.RoleService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Maple
 * @description 用户权限校验
 * @date 2020/2/2 16:14
 */
@Component
public class SysPermissionService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 获取角色数据权限
     *
     * @param sysUser 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser sysUser) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(sysUser.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param sysUser 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser sysUser) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            roles.add("*:*:*");
        } else {
            roles.addAll(menuService.selectMenuPermsByUserId(sysUser.getUserId()));
        }
        return roles;
    }
}
