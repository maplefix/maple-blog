package top.maplefix.controller.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Link;
import top.maplefix.service.LinkService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.page.TableDataInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Maple
 * @description : 友链数据接口
 * @date : 2020/1/28 2:53
 */
@RestController
@RequestMapping("/system/link")
@Slf4j
public class LinkController extends BaseController {

    @Resource
    private LinkService linkService;

    @PreAuthorize("@permissionService.hasPermission('system:link:list')")
    @GetMapping("/list")
    public TableDataInfo list(Link link) {
        startPage();
        List<Link> list = linkService.selectLinkList(link);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('system:link:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable Long  id) {
        return BaseResult.success(linkService.selectLinkById(id));
    }

    @PreAuthorize("@permissionService.hasPermission('system:link:add')")
    @OLog(module = "友链管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public BaseResult add(@RequestBody @Validated Link link) {
        link.setCreateDate(DateUtils.getTime());
        return toResult(linkService.insertLink(link));
    }

    @PreAuthorize("@permissionService.hasPermission('system:link:edit')")
    @OLog(module = "友链管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public BaseResult edit(@RequestBody Link link) {
        link.setUpdateDate(DateUtils.getTime());
        return toResult(linkService.updateLink(link));
    }

    @PreAuthorize("@permissionService.hasPermission('system:link:edit')")
    @OLog(module = "友链管理", businessType = BusinessType.UPDATE)
    @PutMapping("/pass/{id}/{pass}")
    public BaseResult handlePass(@PathVariable Long id, @PathVariable Boolean pass) {
        return toResult(linkService.handleLinkPass(id, pass));
    }

    @PreAuthorize("@permissionService.hasPermission('system:link:remove')")
    @OLog(module = "友链管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult delete(@PathVariable String ids) {
        return toResult(linkService.deleteLinkByIds(ids));
    }
}
