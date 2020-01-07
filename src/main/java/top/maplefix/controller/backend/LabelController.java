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
import top.maplefix.model.Label;
import top.maplefix.service.IBlogLabelService;
import top.maplefix.service.ILabelService;
import top.maplefix.utils.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客标签控制类
 * @date : Created in 2019/7/28 2:52
 * @version : v1.0
 */
@Controller
@RequestMapping("/api/admin/label")
@Slf4j
public class LabelController extends BaseController {

    @Autowired
    private ILabelService labelService;

    @Autowired
    private IBlogLabelService blogLabelService;

    /**
     * 标签页面
     * @param request
     * @return
     */
    @GetMapping({"","/"})
    public String tagPage(HttpServletRequest request) {
        request.setAttribute("path", "label");
        return "admin/label";
    }

    /**
     * 标签数据列表查询
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("博客标签分页查询开始...");
        BaseResult baseResult = new BaseResult();
        if (!checkPageParam(params)) {
            log.error("博客标签分页查询失败,缺少分页参数");
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        List<Label> labelList = labelService.getLabelPage(params);
        PageInfo<Label> pageInfo = new PageInfo<>(labelList);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("博客标签分页查询成功...");
        return baseResult;

    }

    /**
     * 保存标签
     * @param label
     * @return
     */
    @PostMapping("/save")
    @OLog(module = "标签管理", businessType = OperationType.INSERT)
    @ResponseBody
    public BaseResult save(Label label) {
        log.info("博客标签新增操作开始...");
        try {
            Label lab = labelService.isExistLabel(label);
            if (lab != null) {
                log.error("博客标签新增操作失败,该标签{}已存在",lab.getLabelName());
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "标签已存在");
            }
            labelService.saveLabel(label);
            log.info("博客标签新增操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("博客标签编辑操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }

    }

    /**
     * 标签修改
     * @param label
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OLog(module = "标签管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult update(Label label) {
        log.info("博客标签修改操作开始...");
        try {
            labelService.updateLabel(label);
            log.info("博客标签修改操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("博客标签修改操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 删除标签
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @OLog(module = "标签管理", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("博客标签删除操作开始...");
        boolean isExist = blogLabelService.isExistBlogLabel(ids);
        try {
            if(!isExist){
                labelService.deleteBatch(ids);
                return new BaseResult();
            }else {
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "有关联数据请勿强行删除");
            }
        }catch (Exception e){
            log.error("博客标签删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 导出标签列表
     * @param ids 标签ids
     * @return BaseResult excel文件名
     */
    @PostMapping("/export")
    @OLog(module = "标签管理", businessType = OperationType.EXPORT)
    @ResponseBody
    public BaseResult export(String[] ids, HttpServletResponse response) {
        log.info("标签导出操作开始...");
        try {
            List<Label> labelList = labelService.selectLabelByIds(ids);
            ExcelUtil<Label> util = new ExcelUtil<>(Label.class);
            BaseResult baseResult = util.exportExcel(labelList, "blogLabelList", response);
            log.info("标签导出操作成功...");
            return baseResult;
        }catch (Exception e){
            log.error("标签导出操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
}
