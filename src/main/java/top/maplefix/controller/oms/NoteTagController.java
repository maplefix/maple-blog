package top.maplefix.controller.oms;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.enums.TagType;
import top.maplefix.model.Tag;
import top.maplefix.service.TagService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 读书标签接口
 * @date : 2020/2/24 19:42
 */
@RestController
@RequestMapping("book/tag")
public class NoteTagController extends BaseController {

    final TagService tagService;

    public NoteTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PreAuthorize("@permissionService.hasPermission('book:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(Tag tag) {
        startPage();
        tag.setType(TagType.NOTE.getType());
        List<Tag> list = tagService.selectTagList(tag);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('book:tag:add')")
    @OLog(module = "标签管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public BaseResult add(@RequestBody Tag tag) {
        tag.setCreateDate(DateUtils.getTime());
        tag.setType(TagType.NOTE.getType());
        return toResult(tagService.insertTag(tag));
    }

    @PreAuthorize("@permissionService.hasPermission('book:tag:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable String id) {
        return BaseResult.success(tagService.selectTagById(id));
    }

    @PreAuthorize("@permissionService.hasPermission('book:tag:edit')")
    @OLog(module = "标签管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public BaseResult edit(@RequestBody Tag tag) {
        return toResult(tagService.updateTag(tag));
    }

    @PreAuthorize("@permissionService.hasPermission('book:tag:remove')")
    @OLog(module = "标签管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(tagService.deleteTagByIds(ids));
    }
}
