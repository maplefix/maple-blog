package top.maplefix.controller.oms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Blacklist;
import top.maplefix.service.BlacklistService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author Maple
 * @description 黑名单管理数据接口
 * @date 2020/3/18 15:08
 */
@RestController
@RequestMapping("/blacklist")
@Slf4j
public class BlacklistController extends BaseController {
    
    @Autowired
    private BlacklistService blacklistService;

    /**
     * 获取黑名单列表
     */
    @PreAuthorize("@permissionService.hasPermission('monitor:blacklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(Blacklist blacklist) {
        startPage();
        List<Blacklist> list = blacklistService.selectBlacklistList(blacklist);
        return getDataTable(list);
    }

    /**
     * 根据黑名单id获取详细信息
     */
    @PreAuthorize("@permissionService.hasPermission('monitor:blacklist:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable String id) {
        return BaseResult.success(blacklistService.selectBlacklistById(id));
    }

    /**
     * 新增黑名单
     */
    @PreAuthorize("@permissionService.hasPermission('monitor:blacklist:add')")
    @OLog(module = "黑名单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody @Validated Blacklist blacklist) {
        blacklist.setCreateDate(DateUtils.getTime());
        return toResult(blacklistService.insertBlacklist(blacklist));
    }

    /**
     * 修改黑名单
     */
    @PreAuthorize("@permissionService.hasPermission('monitor:blacklist:edit')")
    @OLog(module = "黑名单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody  Blacklist blacklist) {
        blacklist.setUpdateDate(DateUtils.getTime());
        return toResult(blacklistService.updateBlacklist(blacklist));
    }

    /**
     * 删除黑名单
     */
    @PreAuthorize("@permissionService.hasPermission('monitor:blacklist:remove')")
    @OLog(module = "黑名单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(blacklistService.deleteBlacklistByIds(ids));
    }
}
