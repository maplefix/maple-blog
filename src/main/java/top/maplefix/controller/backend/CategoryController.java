package top.maplefix.controller.backend;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.OperationType;
import top.maplefix.enums.ResultCode;
import top.maplefix.model.Category;
import top.maplefix.service.IBlogService;
import top.maplefix.service.ICategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客分类控制类
 * @date : Created in 2019/7/28 2:53
 * @editor: Edited in 2019/10/29 20:42
 * @version: v2.1
 */
@Controller
@RequestMapping("/api/admin/category")
@Slf4j
public class CategoryController extends BaseController {


    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBlogService blogService;

    /**
     * 博客分类页面
     * @param request
     * @return
     */
    @GetMapping({"","/"})
    public String categoryPage(HttpServletRequest request) {
        request.setAttribute("path", "category");
        return "admin/category";
    }

    /**
     * 查询分类列表
     * @param params
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("分类列表分页查询开始...");
        BaseResult baseResult = new BaseResult();
        if (!checkPageParam(params)) {
            baseResult.setCode(ResultCode.LACK_PARAM_CODE.getCode());
            baseResult.setMsg(Constant.SUCCESS_MSG);
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        List<Category> catList = categoryService.getBlogCategoryPage(params);
        PageInfo<Category> pageInfo = new PageInfo<>(catList);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("分类列表分页查询成功...");
        return baseResult;
    }

    /**
     * 分类添加
     * @param category
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @OLog(module = "分类管理", businessType = OperationType.INSERT)
    @ResponseBody
    public BaseResult save(Category category) {
        log.info("新增分类操作开始...");
        try {
            Category cat = categoryService.isExistCategory(category);
            if (cat != null) {
                log.error("分类新增操作失败，该分类:{}已存在...",cat.getCategoryName());
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "分类已存在");
            }
            categoryService.saveCategory(category);
            log.info("分类新增操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("分类新增操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }


    /**
     * 分类修改
     * @param category
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OLog(module = "分类管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult update(Category category) {
        log.info("修改分类操作开始...");
        try {
            categoryService.updateCategory(category);
            log.info("分类修改操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("分类修改操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }

    }


    /**
     * 分类删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @OLog(module = "分类管理", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("删除分类操作开始...");
        //查询该分类是否有博客使用
        boolean isExist = blogService.isExistBlogCategory(ids);
        try {
            if(!isExist){
                categoryService.deleteBatch(ids);
                log.info("分类删除操作成功...");
                return new BaseResult();
            }else {
                log.error("分类删除操作失败，有关联数据...");
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "有关联数据请勿强行删除");
            }
        }catch (Exception e){
            log.error("分类删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

}
