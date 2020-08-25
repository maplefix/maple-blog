package top.maplefix.controller.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.UserConstant;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.SysUser;
import top.maplefix.service.RoleService;
import top.maplefix.service.UserService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.SecurityUtils;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 后端登录授权操作
 * @date : 20220/1/27 14:43
 */
@RestController
@RequestMapping("/system/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@permissionService.hasPermission('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@permissionService.hasPermission('system:user:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable Long  id) {
        BaseResult baseResult = BaseResult.success(userService.selectUserById(id));
        baseResult.put("roleIds", roleService.selectRoleListByUserId(id));
        return baseResult;
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@permissionService.hasPermission('system:user:add')")
    @OLog(module = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody SysUser user) {
        if (UserConstant.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return BaseResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (UserConstant.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return BaseResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (UserConstant.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return BaseResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateDate(DateUtils.getTime());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toResult(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@permissionService.hasPermission('system:user:edit')")
    @OLog(module = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (UserConstant.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return BaseResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (UserConstant.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return BaseResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateDate(DateUtils.getTime());
        return toResult(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@permissionService.hasPermission('system:user:remove')")
    @OLog(module = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(userService.deleteUserByIds(ids));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@permissionService.hasPermission('system:user:edit')")
    @OLog(module = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public BaseResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateDate(DateUtils.getTime());
        return toResult(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@permissionService.hasPermission('system:user:edit')")
    @OLog(module = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public BaseResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUpdateDate(DateUtils.getTime());
        return toResult(userService.updateUserStatus(user));
    }

}
