package top.maplefix.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.maplefix.model.User;
import top.maplefix.secrrity.LoginUser;
import top.maplefix.exception.CustomException;

/**
 * @author Maple
 * @description 安全校验工具类
 * @date 2020/1/18 15:19
 */
@Slf4j
public class SecurityUtils {
    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getOnlinUser().getUser().getUserName();
        } catch (Exception e) {
            throw new CustomException("获取用户账户异常", org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getOnlinUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new CustomException("获取用户信息异常", org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 判断当前用户数是不是admin
     */
    public static boolean isAdmin() {
        try {
            LoginUser loginUser = (LoginUser) getAuthentication().getPrincipal();
            return loginUser.getUser().isAdmin();
        } catch (Exception e) {
            //ignore it
            return false;
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(String userId) {
        return User.ADMIN.equals(userId);
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("admin123"));
    }
}
