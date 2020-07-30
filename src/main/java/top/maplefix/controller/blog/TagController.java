package top.maplefix.controller.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Tag;
import top.maplefix.service.TagService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 博客标签接口
 * @date : 2019/7/28 2:52
 */
@RestController
@RequestMapping("/blog/tag")
@Slf4j
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    /**
     * 查询博客标签列表
     * @param tag 查询条件
     * @return list
     */
    @PreAuthorize("@permissionService.hasPermission('blog:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(Tag tag) {
        startPage();
        List<Tag> list = tagService.selectTagList(tag);
        return getDataTable(list);
    }

    /**
     * 新增标签
     * @param tag tag
     * @return list
     */
    @PreAuthorize("@permissionService.hasPermission('blog:tag:add')")
    @OLog(module = "标签管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public BaseResult add(@RequestBody Tag tag) {
        return toResult(tagService.insertTag(tag));
    }

    /**
     * 根据id查询标签
     * @param id id
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:tag:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable String id) {
        return BaseResult.success(tagService.selectTagById(id));
    }

    /**
     * 更新标签
     * @param tag tag
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:tag:edit')")
    @OLog(module = "标签管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public BaseResult edit(@RequestBody Tag tag) {
        return toResult(tagService.updateTag(tag));
    }

    /**
     * 删除标签
     * @param ids id集合
     * @return  BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:tag:remove')")
    @OLog(module = "标签管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(tagService.deleteTagByIds(ids));
    }
}
