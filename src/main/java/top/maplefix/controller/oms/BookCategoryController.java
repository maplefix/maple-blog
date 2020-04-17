package top.maplefix.controller.oms;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Category;
import top.maplefix.service.BookCategoryService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 读书分类管理接口
 * @date : 2020/3/24 18:42
 */
@RestController
@RequestMapping("book/category")
public class BookCategoryController extends BaseController {
    final BookCategoryService bookCategoryService;

    public BookCategoryController(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    @PreAuthorize("@permissionService.hasPermission('book:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(Category category) {
        startPage();
        List<Category> list = bookCategoryService.selectBookCategoryList(category);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('book:category:add')")
    @OLog(module = "分类管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public BaseResult add(@RequestBody @Validated Category category) {
        category.setCreateDate(DateUtils.getTime());
        return toResult(bookCategoryService.insertBookCategory(category));
    }

    @PreAuthorize("@permissionService.hasPermission('book:category:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable String id) {
        return BaseResult.success(bookCategoryService.selectBookCategoryById(id));
    }

    @PreAuthorize("@permissionService.hasPermission('book:category:edit')")
    @OLog(module = "分类管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public BaseResult edit(@RequestBody @Validated Category category) {
        return toResult(bookCategoryService.updateBookCategory(category));
    }

    @PreAuthorize("@permissionService.hasPermission('book:category:remove')")
    @OLog(module = "分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(bookCategoryService.deleteBookCategoryByIds(ids));
    }
}
