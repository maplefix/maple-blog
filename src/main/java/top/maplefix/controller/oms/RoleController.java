package top.maplefix.controller.oms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.UserConstant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Role;
import top.maplefix.service.RoleService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author Maple
 * @description 角色数据接口
 * @date 2020/3/16 13:50
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController extends BaseController {
    
    @Autowired
    private RoleService roleService;

    @PreAuthorize("@permissionService.hasPermission('system:role:list')")
    @GetMapping("/list")
    public TableDataInfo list(Role role) {
        startPage();
        List<Role> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @PreAuthorize("@permissionService.hasPermission('system:role:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable String id) {
        return BaseResult.success(roleService.selectRoleById(id));
    }

    /**
     * 新增角色
     */
    @PreAuthorize("@permissionService.hasPermission('system:role:add')")
    @OLog(module = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody Role role) {
        if (UserConstant.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return BaseResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstant.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return BaseResult.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateDate(DateUtils.getTime());
        return toResult(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @PreAuthorize("@permissionService.hasPermission('system:role:edit')")
    @OLog(module = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody Role role) {
        roleService.checkRoleAllowed(role);
        if (UserConstant.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return BaseResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstant.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return BaseResult.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateDate(DateUtils.getTime());
        return toResult(roleService.updateRole(role));
    }

    /**
     * 修改保存数据权限
     */
    @PreAuthorize("@permissionService.hasPermission('system:role:edit')")
    @OLog(module = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public BaseResult dataScope(@RequestBody Role role) {
        roleService.checkRoleAllowed(role);
        return toResult(roleService.authDataScope(role));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@permissionService.hasPermission('system:role:edit')")
    @OLog(module = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public BaseResult changeStatus(@RequestBody Role role) {
        roleService.checkRoleAllowed(role);
        role.setUpdateDate(DateUtils.getTime());
        return toResult(roleService.updateRoleStatus(role));
    }

    @PreAuthorize("@permissionService.hasPermission('system:role:remove')")
    @OLog(module = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(roleService.deleteRoleByIds(ids));
    }

    /**
     * 获取角色选择框列表
     */
    @PreAuthorize("@permissionService.hasPermission('system:role:query')")
    @GetMapping("/optionselect")
    public BaseResult optionselect() {
        return BaseResult.success(roleService.selectRoleAll());
    }
}
