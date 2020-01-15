package top.maplefix.common;

import lombok.Data;

import java.util.Set;

/**
 * @author Maple
 * @description 在线用户信息
 * @date 2020/1/14 17:25
 */
@Data
public class OnlineUser {

    /**
     * 用户表主键
     */
    private String userId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 性别
     */
    private String gender;
    /**
     * 头像相对路径
     */
    private String avatar;
    /**
     * token
     */
    private String token;
    /**
     * 登陆时间
     */
    private Long loginTime;
    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 登陆IP
     */
    private String ip;
    /**
     * 登陆地区
     */
    private String location;
    /**
     * 登陆浏览器
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 权限集合
     */
    private Set<String> permissions;
}
