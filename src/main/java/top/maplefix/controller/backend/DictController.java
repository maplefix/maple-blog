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
import top.maplefix.model.Dict;
import top.maplefix.service.IDictService;
import top.maplefix.utils.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客字典项控制类
 * @date : Created in 2019/7/28 16:36
 * @editor:
 * @version: v2.1
 */
@Controller
@RequestMapping("/api/admin/dict")
@Slf4j
public class DictController extends BaseController {

    @Autowired
    private IDictService dictService;

    @GetMapping({"","/"})
    public String linkPage(HttpServletRequest request) {
        request.setAttribute("path", "dict");
        return "admin/dict";
    }

    /**
     * 字典查询
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("字典分页查询开始...");
        BaseResult baseResult = new BaseResult();
        if (!checkPageParam(params)) {
            baseResult.setCode(ResultCode.LACK_PARAM_CODE.getCode());
            baseResult.setMsg(Constant.SUCCESS_MSG);
            log.info("字典分页查询失败,缺少分页参数...");
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        List<Dict> dictList = dictService.getDictPage(params);
        PageInfo<Dict> pageInfo = new PageInfo<>(dictList);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("字典分页查询成功...");
        return baseResult;

    }

    /**
     * 字典添加
     * @param dict
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @OLog(module = "字典管理", businessType = OperationType.INSERT)
    @ResponseBody
    public BaseResult save(Dict dict) {
        log.info("字典新增操作开始...");
        try {
            Dict dict1 = dictService.isExistDict(dict);
            if (dict1 != null) {
                log.info("字典新增操作失败,该关键字:{}已存在",dict1.getKeyWord());
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "关键字已存在");
            }
            dictService.saveDict(dict);
            log.info("字典新增操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("字典新增操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @OLog(module = "字典管理", businessType = OperationType.OTHER)
    @ResponseBody
    public BaseResult info(@PathVariable("id") String id) {
        log.info("字典详情查询操作开始...");
        BaseResult baseResult = new BaseResult();
        Dict dict = dictService.selectById(id);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(dict);
        log.info("字典详情查询操作成功...");
        return baseResult;
    }

    /**
     * 字典修改
     * @param dict
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OLog(module = "字典管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult update(Dict dict) {
        log.info("字典编辑操作开始...");
        try {
            dictService.updateDict(dict);
            log.info("字典编辑操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("字典编辑操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 字典删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @OLog(module = "字典管理", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("字典删除操作开始...");
        try {
            dictService.deleteBatch(ids);
            log.info("字典删除操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("字典删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 导出字典列表
     * @param ids 字典ids
     * @return BaseResult excel文件名
     */
    @PostMapping("/export")
    @OLog(module = "字典管理", businessType = OperationType.EXPORT)
    @ResponseBody
    public BaseResult export(String[] ids, HttpServletResponse response) {
        log.info("字典导出操作开始...");
        try {
            List<Dict> dictList = dictService.selectDictByIds(ids);
            ExcelUtil<Dict> util = new ExcelUtil<>(Dict.class);
            BaseResult baseResult = util.exportExcel(dictList, "dictList", response);
            log.info("字典导出操作成功...");
            return baseResult;
        }catch (Exception e){
            log.error("字典导出操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
}
