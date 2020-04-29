package top.maplefix.service.impl;

import org.springframework.stereotype.Service;
import top.maplefix.secrrity.LoginUser;
import top.maplefix.service.UserOnlineService;
import top.maplefix.utils.StringUtils;
import top.maplefix.vo.UserOnline;

/**
 * @author Maple
 * @description 当前在线会话实现类
 * @date 2020/3/20 10:35
 */
@Service
public class UserOnlineServiceImpl implements UserOnlineService {

    @Override
    public UserOnline selectOnlineByIpAddr(String ipaddr, LoginUser user) {
        if (StringUtils.equals(ipaddr, user.getIp())) {
            return loginUserToUserOnline(user);
        }
        return null;
    }

    @Override
    public UserOnline selectOnlineByUserName(String userName, LoginUser user) {
        if (StringUtils.equals(userName, user.getUsername())) {
            return loginUserToUserOnline(user);
        }
        return null;
    }


    @Override
    public UserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user) {
        if (StringUtils.equals(ipaddr, user.getIp()) && StringUtils.equals(userName, user.getUsername())) {
            return loginUserToUserOnline(user);
        }
        return null;
    }

    @Override
    public UserOnline loginUserToUserOnline(LoginUser user) {
        if (StringUtils.isNull(user) && StringUtils.isNull(user.getUser())) {
            return null;
        }
        UserOnline userOnline = new UserOnline();
        userOnline.setTokenId(user.getUserToken());
        userOnline.setUserName(user.getUsername());
        userOnline.setIpAddr(user.getIp());
        userOnline.setLoginLocation(user.getLocation());
        userOnline.setBrowser(user.getBrowser());
        userOnline.setOs(user.getOs());
        userOnline.setLoginTime(user.getLoginTime());
        return userOnline;
    }
}
