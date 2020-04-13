package top.maplefix.controller.oms;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.model.Menu;
import top.maplefix.model.SysUser;
import top.maplefix.secrrity.LoginUser;
import top.maplefix.secrrity.service.SysLoginService;
import top.maplefix.secrrity.service.SysPermissionService;
import top.maplefix.secrrity.service.TokenService;
import top.maplefix.service.MenuService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author : Maple
 * @description : 登录操作控制类
 * @date : 2020/1/28 16:37
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 登录方法
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    @PostMapping("/login")
    public BaseResult login(String username, String password, String code, String uuid) {
        BaseResult baseResult = BaseResult.success();
        // 生成令牌
        String token = loginService.login(username, password, code, uuid);
        JSONObject data = new JSONObject();
        data.put(Constant.TOKEN, token);
        baseResult.setData(data);
        return baseResult;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public BaseResult getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(request);
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        BaseResult baseResult = BaseResult.success();
        JSONObject data = new JSONObject();
        data.put("user", user);
        data.put("roles", roles);
        data.put("permissions", permissions);
        baseResult.setData(data);
        return baseResult;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public BaseResult getRouters() {
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 用户信息
        SysUser user = loginUser.getUser();
        List<Menu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return BaseResult.success(menuService.buildMenus(menus));
    }
}
