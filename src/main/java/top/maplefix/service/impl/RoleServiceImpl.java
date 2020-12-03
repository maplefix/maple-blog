package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.maplefix.constant.Constant;
import top.maplefix.exception.CustomException;
import top.maplefix.mapper.RoleMapper;
import top.maplefix.mapper.RoleMenuMidMapper;
import top.maplefix.mapper.UserRoleMidMapper;
import top.maplefix.model.Role;
import top.maplefix.model.RoleMenuMid;
import top.maplefix.service.RoleService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.StringUtils;

import java.util.*;

/**
 * @author Maple
 * @description 角色信息接口实现类
 * @date 2020/1/18 10:35
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMidMapper roleMenuMapper;
    @Autowired
    private UserRoleMidMapper userRoleMapper;

    @Override
    public List<Role> selectRoleList(Role role) {
        return roleMapper.selectRoleList(role);
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<Role> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Role> selectRoleAll() {
        return roleMapper.selectRoleAll();
    }

    @Override
    public List<Integer> selectRoleListByUserId(Long  userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }

    @Override
    public Role selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    @Override
    public String checkRoleNameUnique(Role role) {
        Long  roleId = role.getId();
        Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && !info.getId().equals(roleId)) {
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public String checkRoleKeyUnique(Role role) {
        Long  roleId = role.getId();
        Role info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && !info.getId().equals(roleId)) {
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public void checkRoleAllowed(Role role) {
        if (StringUtils.isNotNull(role.getId()) && role.isAdmin()) {
            throw new CustomException("不允许操作超级管理员角色");
        }
    }

    @Override
    @Transactional
    public int insertRole(Role role) {
        // 新增角色信息
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    @Override
    @Transactional
    public int updateRole(Role role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getId());
        return insertRoleMenu(role);
    }

    @Override
    public int updateRoleStatus(Role role) {
        return roleMapper.updateRole(role);
    }


    @Override
    @Transactional
    public int authDataScope(Role role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        return 1;
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(Role role) {
        int rows = 1;
        // 新增用户与角色管理
        List<RoleMenuMid> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            RoleMenuMid rm = new RoleMenuMid();
            rm.setRoleId(role.getId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    @Override
    public int deleteRoleByIds(String ids) {
        Long[] roleIds = ConvertUtils.toLongArray(ids);
        for (Long roleId : roleIds) {
            checkRoleAllowed(new Role(roleId));
            Role role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new CustomException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return roleMapper.deleteRoleByIds(roleIds);
    }

    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }
}
