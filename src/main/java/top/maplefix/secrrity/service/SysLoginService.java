package top.maplefix.secrrity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import top.maplefix.config.factory.AsyncFactory;
import top.maplefix.config.factory.AsyncManager;
import top.maplefix.constant.Constant;
import top.maplefix.exception.CustomException;
import top.maplefix.exception.user.CaptchaException;
import top.maplefix.exception.user.CaptchaExpireException;
import top.maplefix.exception.user.UserPasswordNotMatchException;
import top.maplefix.redis.RedisCacheService;
import top.maplefix.secrrity.LoginUser;
import top.maplefix.utils.MessageUtils;

import javax.annotation.Resource;

/**
 * @author Maple
 * @description 系统用户登陆校验
 * @date 2020/2/2 16:13
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code  验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        String verifyKey = Constant.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCacheService.getCacheObject(verifyKey);
        redisCacheService.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLoginLog(username, false, MessageUtils.message("user.captcha.error")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLoginLog(username, false, MessageUtils.message("user.captcha.expire")));
            throw new CaptchaException();
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLoginLog(username, false, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLoginLog(username, false, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLoginLog(username, true, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
