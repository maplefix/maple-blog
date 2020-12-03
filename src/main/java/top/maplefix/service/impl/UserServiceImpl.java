package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.maplefix.constant.UserConstant;
import top.maplefix.exception.CustomException;
import top.maplefix.mapper.RoleMapper;
import top.maplefix.mapper.UserMapper;
import top.maplefix.mapper.UserRoleMidMapper;
import top.maplefix.model.Role;
import top.maplefix.model.SysUser;
import top.maplefix.model.UserRoleMid;
import top.maplefix.secrrity.LoginUser;
import top.maplefix.secrrity.service.TokenService;
import top.maplefix.service.UserService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.SecurityUtils;
import top.maplefix.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Maple
 * @description :用户操作接口实现类
 * @date : 2020/1/24 23:00 
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMidMapper userRoleMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public String selectUserRoleGroup(String userName) {
        List<Role> list = roleMapper.selectRolesByUserName(userName);
        StringBuilder idsStr = new StringBuilder();
        for (Role role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstant.NOT_UNIQUE;
        }
        return UserConstant.UNIQUE;
    }

    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhone());
        if (StringUtils.isNotNull(info) && !info.getId().equals(userId) ) {
            return UserConstant.NOT_UNIQUE;
        }
        return UserConstant.UNIQUE;
    }

    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();;
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && !info.getId().equals(userId) ) {
            return UserConstant.NOT_UNIQUE;
        }
        return UserConstant.UNIQUE;
    }

    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getId()) && user.isAdmin()) {
            throw new CustomException("不允许操作超级管理员用户");
        }
    }

    @Override
    @Transactional
    public int insertUser(SysUser user) {
        user.setCreateDate(DateUtils.getTime());
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        return userMapper.updateUser(user);
    }


    @Override
    public int updateUserStatus(SysUser user) {
        user.setUpdateDate(DateUtils.getTime());
        return userMapper.updateUser(user);
    }


    @Override
    public int updateUserProfile(SysUser user) {
        user.setId(SecurityUtils.getLoginUser().getUser().getId());
        int result = userMapper.updateUser(user);
        //更新redis缓存
        refreshTokenClaims(user.getId());
        return result;
    }

    /**
     * 同步刷新Redis缓存
     *
     * @param id sys_user id
     */
    private void refreshTokenClaims(Long id) {
        //更新redis缓存
        LoginUser loginUser = SecurityUtils.getLoginUser();
        loginUser.setUser(userMapper.selectUserById(id));
        tokenService.refreshToken(loginUser);
    }

    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }


    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<UserRoleMid> list = new ArrayList<>();
            for (Long roleId : roles) {
                UserRoleMid ur = new UserRoleMid();
                ur.setUserId(user.getId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (!list.isEmpty()) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }


    @Override
    @Transactional
    public int deleteUserByIds(String ids) {
        Long[] userIds = ConvertUtils.toLongArray(ids);
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
            // 删除用户与角色关联
            userRoleMapper.deleteUserRoleByUserId(userId);
        }
        return userMapper.deleteUserByIds(userIds);
    }
}

