package top.maplefix.controller.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Comment;
import top.maplefix.service.CommentService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author Maple
 * @description 评论controller
 * @date 2020/2/2 21:55
 */
@Slf4j
@RestController
@RequestMapping("/blog/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    /**
     * 分页查询评论列表
     * @param comment comment
     * @return TableDataInfo
     */
    @PreAuthorize("@permissionService.hasPermission('blog:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Comment comment) {
        startPage();
        List<Comment> list = commentService.selectCommentList(comment);
        return getDataTable(list);
    }

    /**
     * 获取评论详情
     * @param id 评论id
     * @return  BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:comment:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable Long id) {
        return BaseResult.success(commentService.selectCommentById(id));
    }

    /**
     * 新增评论
     * @param comment comment
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:comment:add')")
    @OLog(module = "评论管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody @Validated Comment comment) {
        return toResult(commentService.insertComment(comment));
    }

    /**
     * 编辑评论
     * @param comment comment
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:comment:edit')")
    @OLog(module = "评论管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody @Validated Comment comment) {
        return toResult(commentService.updateComment(comment));
    }

    /**
     * 隐藏或者显示评论
     * @param id 评论id
     * @param display 是否显示
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:comment:edit')")
    @OLog(module = "评论管理", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/display/{display}")
    public BaseResult edit(@PathVariable Long id, @PathVariable Boolean display) {
        Comment comment = new Comment();
        comment.setDisplay(display? Constant.DISPLAY:Constant.HIDE);
        comment.setId(id);
        return toResult(commentService.updateComment(comment));
    }

    /**
     * 删除评论
     * @param ids id数组
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('system:config:remove')")
    @OLog(module = "评论管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(commentService.deleteCommentByIds(ids));
    }
}
