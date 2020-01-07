package top.maplefix.service;

import top.maplefix.model.User;

/**
 * @author : Maple
 * @description : 用户操作接口
 * @date : Created in 2019/7/24 22:59
 * @version : v1.0
 */
public interface IUserService {

    /**
     * 根据用户名密码登录
     * @param loginName 登录名
     * @param password 密码
     * @return
     */
    User login(String loginName, String password);

    /**
     * 根据条件插叙一条user信息
     * @param user
     * @return
     */
    User getUserDetail(User user);

    /**
     * 修改密码
     * @param userId 用户id
     * @param originalPassword 当前密码
     * @param newPassword 新密码
     * @return
     */
    boolean updatePassword(String userId, String originalPassword, String newPassword);

    /**
     * 修改用户登录名和用户名
     * @param user
     * @return
     */
    boolean updateUser(User user);
}
