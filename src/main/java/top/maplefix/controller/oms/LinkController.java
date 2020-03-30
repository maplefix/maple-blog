package top.maplefix.controller.oms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
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
@Controller
@RequestMapping("/link")
@Slf4j
public class LinkController extends BaseController {

    @Resource
    private LinkService linkService;

    @PreAuthorize("@permissionService.hasPermission('blog:link:list')")
    @GetMapping("/list")
    public TableDataInfo list(Link link) {
        startPage();
        List<Link> list = linkService.selectLinkList(link);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('blog:link:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable String  id) {
        return BaseResult.success(linkService.selectLinkById(id));
    }

    @PreAuthorize("@permissionService.hasPermission('blog:link:add')")
    @OLog(module = "友链管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public BaseResult add(@RequestBody @Validated Link link) {
        link.setCreateDate(DateUtils.getTime());
        return toResult(linkService.insertLink(link));
    }

    @PreAuthorize("@permissionService.hasPermission('blog:link:edit')")
    @OLog(module = "友链管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public BaseResult edit(@RequestBody Link link) {
        link.setUpdateDate(DateUtils.getTime());
        return toResult(linkService.updateLink(link));
    }

    @PreAuthorize("@permissionService.hasPermission('blog:link:edit')")
    @OLog(module = "友链管理", businessType = BusinessType.UPDATE)
    @PutMapping("/pass/{id}/{pass}")
    public BaseResult handlePass(@PathVariable String id, @PathVariable Boolean pass) {
        return toResult(linkService.handleLinkPass(id, pass));
    }

    @PreAuthorize("@permissionService.hasPermission('blog:link:remove')")
    @OLog(module = "友链管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult delete(@PathVariable String ids) {
        return toResult(linkService.deleteLinkByIds(ids));
    }
}
