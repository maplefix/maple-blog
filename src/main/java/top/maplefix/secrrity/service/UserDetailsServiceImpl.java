package top.maplefix.secrrity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.maplefix.enums.UserStatus;
import top.maplefix.exception.BaseException;
import top.maplefix.model.SysUser;
import top.maplefix.secrrity.LoginUser;
import top.maplefix.service.UserService;
import top.maplefix.utils.StringUtils;

/**
 * @author Maple
 * @description  自定义用户验证处理
 * @date 2020/2/2 16:15
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }

        return createLoginUser(sysUser);
    }

    public UserDetails createLoginUser(SysUser sysUser) {
        return new LoginUser(sysUser, permissionService.getMenuPermission(sysUser));
    }
}
