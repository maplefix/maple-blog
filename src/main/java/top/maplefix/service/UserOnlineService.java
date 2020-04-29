package top.maplefix.service;

import top.maplefix.secrrity.LoginUser;
import top.maplefix.vo.UserOnline;

/**
 * @author Maple
 * @description 在线用户查询service
 * @date 2020/3/20 10:33
 */
public interface UserOnlineService {

    /**
     * 通过登录地址查询信息
     *
     * @param ipAddr 登录地址
     * @param user   用户信息
     * @return 在线用户信息
     */
    UserOnline selectOnlineByIpAddr(String ipAddr, LoginUser user);

    /**
     * 通过用户名称查询信息
     *
     * @param userName 用户名称
     * @param user     用户信息
     * @return 在线用户信息
     */
    UserOnline selectOnlineByUserName(String userName, LoginUser user);

    /**
     * 通过登录地址/用户名称查询信息
     *
     * @param ipAddr   登录地址
     * @param userName 用户名称
     * @param user     用户信息
     * @return 在线用户信息
     */
    UserOnline selectOnlineByInfo(String ipAddr, String userName, LoginUser user);

    /**
     * 设置在线用户信息
     *
     * @param user 用户信息
     * @return 在线用户
     */
    UserOnline loginUserToUserOnline(LoginUser user);
}
