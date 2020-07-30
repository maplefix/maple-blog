package top.maplefix.controller.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Category;
import top.maplefix.service.CategoryService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 博客分类数据接口
 * @date : 2020/1/28 2:53
 */
@RestController
@RequestMapping("/blog/category")
@Slf4j
public class CategoryController extends BaseController {


    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询分类列表
     * @param category category
     * @return TableDataInfo
     */
    @PreAuthorize("@permissionService.hasPermission('blog:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(Category category) {
        startPage();
        List<Category> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }
    /**
     * 新增分类
     * @param category category
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:category:add')")
    @OLog(module = "分类管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public BaseResult add(@RequestBody @Validated Category category) {
        return toResult(categoryService.insertCategory(category));
    }
    /**
     * 获取分类详情
     * @param id 分类id
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:category:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable String id) {
        return BaseResult.success(categoryService.selectCategoryById(id));
    }
    /**
     * 编辑分类
     * @param category 分类
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:category:edit')")
    @OLog(module = "分类管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public BaseResult edit(@RequestBody @Validated Category category) {
        return toResult(categoryService.updateCategory(category));
    }
    /**
     * 删除分类
     * @param ids 分类id集合
     * @return BaseResult
     */
    @PreAuthorize("@permissionService.hasPermission('blog:category:remove')")
    @OLog(module = "分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(categoryService.deleteCategoryByIds(ids));
    }
}
