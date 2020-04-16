package top.maplefix.controller.oms;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.SysUser;
import top.maplefix.secrrity.LoginUser;
import top.maplefix.secrrity.service.TokenService;
import top.maplefix.service.UserService;
import top.maplefix.utils.SecurityUtils;
import top.maplefix.utils.ServletUtils;

/**
 * @author Maple
 * @description 用户信息维护数据接口
 * @date 2020/4/16 11:10
 */
@RestController
@RequestMapping("/user/profile")
public class ProfileController extends BaseController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TokenService tokenService;

    /**
     * 个人信息
     */
    @GetMapping
    public BaseResult profile() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        BaseResult baseResult = BaseResult.success(user);
        JSONObject data = new JSONObject();
        data.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        baseResult.setData(data);
        return baseResult;
    }

    /**
     * 修改用户
     */
    @OLog(module = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult updateProfile(@RequestBody SysUser user) {
        return toResult(userService.updateUserProfile(user));
    }

    /**
     * 重置密码
     */
    @OLog(module = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public BaseResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return BaseResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return BaseResult.error("新密码不能与旧密码相同");
        }
        return toResult(userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)));
    }
}
