package top.maplefix.controller.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Blog;
import top.maplefix.service.BlogService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description :博客数据接口
 * @date : 2020/2/27 16:33
 */
@RestController
@RequestMapping("/blog/blog")
public class BlogController extends BaseController {

    @Autowired
    private BlogService blogService;

    /**
     * 分页查询博客列表
     * @param blog blog
     * @return  TableDataInfo
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:list')")
    @GetMapping("/list")
    public TableDataInfo list(Blog blog) {
        startPage();
        List<Blog> list = blogService.selectBlogList(blog);
        return getDataTable(list);
    }

    /**
     *  新增博客
     * @param blog blog
     * @return  BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:add')")
    @OLog(module = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public BaseResult add(@RequestBody Blog blog) {
        return toResult(blogService.insertBlog(blog));
    }

    /**
     * 保存为草稿
     * @param blog blog
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:add')")
    @OLog(module = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("draft")
    public BaseResult draft( @RequestBody Blog blog) {
        return toResult(blogService.insertBlog(blog));
    }

    /**
     * 更新博客
     * @param blog blog
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:edit')")
    @OLog(module = "博客管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public BaseResult edit(@RequestBody Blog blog) {
        return toResult(blogService.updateBlog(blog));
    }

    /**
     * 编辑草稿博客
     * @param blog blog
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:edit')")
    @OLog(module = "博客管理", businessType = BusinessType.UPDATE)
    @PutMapping("draft")
    public BaseResult editDraft(@RequestBody Blog blog) {
        return toResult(blogService.updateBlog(blog));
    }
    /**
     * 更新推荐博客
     * @param blog blog
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:edit')")
    @OLog(module = "博客管理", businessType = BusinessType.UPDATE)
    @PutMapping("support")
    public BaseResult editSupport(@RequestBody Blog blog) {
        return toResult(blogService.updateBlog(blog));
    }
    /**
     * 更新博客评论
     * @param blog blog
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:edit')")
    @OLog(module = "博客管理", businessType = BusinessType.UPDATE)
    @PutMapping("comment")
    public BaseResult editComment(@RequestBody Blog blog) {
        return toResult(blogService.updateBlog(blog));
    }
    /**
     * 查询博客详情
     * @param id 博客id
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:query')")
    @GetMapping("/{id}")
    public BaseResult getInfo(@PathVariable Long id) {
        return BaseResult.success(blogService.selectBlogById(id));
    }
    /**
     * 删除博客
     * @param id 博客id
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:remove')")
    @OLog(module = "博客管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public BaseResult remove(@PathVariable("id") Long id) {
        return toResult(blogService.deleteBlogById(id));
    }
    /**
     * 查询博客标签集合
     * @param query 查询条件
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:blog:edit')")
    @GetMapping("tag/{query}")
    public TableDataInfo getCommonTag(@PathVariable String query) {
        return getDataTable(blogService.selectBlogTagList(query));
    }

}
