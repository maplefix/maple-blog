package top.maplefix.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Config;
import top.maplefix.service.ConfigService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author wangjg
 * @description 数据配置接口
 * @date 2020/8/12 10:10
 */
@RestController
@RequestMapping("/system/config")
public class ConfigController extends BaseController {
    
    @Autowired
    private ConfigService configService;

    @PreAuthorize("@permissionService.hasPermission('system:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(Config config) {
        startPage();
        List<Config> list = configService.selectConfigList(config);
        return getDataTable(list);
    }
    /**
     * 查询数据详细
     */
    @PreAuthorize("@permissionService.hasPermission('system:config:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable Long id) {
        return BaseResult.success(configService.selectConfigById(id));
    }

    /**
     * 根据类型查询数据信息
     */
    @PreAuthorize("@permissionService.hasPermission('system:config:query')")
    @GetMapping(value = "/configKey/{configKey}")
    public BaseResult dictType(@PathVariable String configKey) {
        return BaseResult.success(configService.selectConfigByKey(configKey));
    }

    /**
     * 新增数据配置
     */
    @PreAuthorize("@permissionService.hasPermission('system:config:add')")
    @OLog(module = "参数配置", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody Config config) {
        return toResult(configService.insertConfig(config));
    }

    /**
     * 修改数据配置
     */
    @PreAuthorize("@permissionService.hasPermission('system:config:edit')")
    @OLog(module = "参数配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody Config config) {
        return toResult(configService.updateConfig(config));
    }

    /**
     * 删除数据配置
     */
    @PreAuthorize("@permissionService.hasPermission('system:config:remove')")
    @OLog(module = "参数配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCode}")
    public BaseResult remove(@PathVariable Long id) {
        return toResult(configService.deleteConfigById(id));
    }
}
