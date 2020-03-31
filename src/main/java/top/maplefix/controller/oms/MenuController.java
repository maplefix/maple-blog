package top.maplefix.controller.oms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.UserConstant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Menu;
import top.maplefix.service.MenuService;
import top.maplefix.utils.DateUtils;

import java.util.List;

/**
 * @author Maple
 * @description 菜单数据接口
 * @date 2020/3/16 13:51
 */
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController extends BaseController {
    
    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     */
    @PreAuthorize("@permissionService.hasPermission('system:menu:list')")
    @GetMapping("/list")
    public BaseResult list(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu);
        return BaseResult.success(menuService.buildMenuTree(menus));
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @PreAuthorize("@permissionService.hasPermission('system:menu:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable String  id) {
        return BaseResult.success(menuService.selectMenuById(id));
    }

    /**
     * 获取菜单下拉树列表
     */
    @PreAuthorize("@permissionService.hasPermission('system:menu:query')")
    @GetMapping("/treeselect")
    public BaseResult treeselect(Menu dept) {
        List<Menu> menus = menuService.selectMenuList(dept);
        return BaseResult.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @PreAuthorize("@permissionService.hasPermission('system:menu:query')")
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    @ResponseBody
    public BaseResult roleMenuTreeselect(@PathVariable("roleId") String  roleId) {
        return BaseResult.success(menuService.selectMenuListByRoleId(roleId));
    }

    /**
     * 新增菜单
     */
    @PreAuthorize("@permissionService.hasPermission('system:menu:add')")
    @OLog(module = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody @Validated Menu menu) {
        if (UserConstant.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return BaseResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setCreateDate(DateUtils.getTime());
        return toResult(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("@permissionService.hasPermission('system:menu:edit')")
    @OLog(module = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody Menu menu) {
        if (UserConstant.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return BaseResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setUpdateDate(DateUtils.getTime());
        return toResult(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("@permissionService.hasPermission('system:menu:remove')")
    @OLog(module = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public BaseResult remove(@PathVariable("id") String  id) {
        if (menuService.hasChildByMenuId(id)) {
            return BaseResult.error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(id)) {
            return BaseResult.error("菜单已分配,不允许删除");
        }
        return toResult(menuService.deleteMenuById(id));
    }
}
