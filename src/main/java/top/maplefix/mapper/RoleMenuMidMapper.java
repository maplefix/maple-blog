package top.maplefix.mapper;

import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.RoleMenuMid;

import java.util.List;

/**
 * @author Maple
 * @description 角色菜单关联
 * @date 2020/2/2 16:43
 */
public interface RoleMenuMidMapper extends Mapper<RoleMenuMid> {
    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int checkMenuExistRole(Long menuId);

    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleMenuByRoleId(Long roleId);

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    int batchRoleMenu(List<RoleMenuMid> roleMenuList);
}
