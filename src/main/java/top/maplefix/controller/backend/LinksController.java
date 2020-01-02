package top.maplefix.controller.backend;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.OperationType;
import top.maplefix.enums.ResultCode;
import top.maplefix.model.Links;
import top.maplefix.service.ILinksService;
import top.maplefix.utils.ExcelUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 友链控制类
 * @date : Created in 2019/7/28 2:53
 * @version : v2.1
 */
@Controller
@RequestMapping("/api/admin/links")
@Slf4j
public class LinksController extends BaseController {

    @Resource
    private ILinksService linksService;

    /**
     * 友链页面
     * @param request
     * @return
     */
    @GetMapping({"","/"})
    public String linkPage(HttpServletRequest request) {
        request.setAttribute("path", "links");
        return "admin/links";
    }

    /**
     * 查询友链列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("友链分页查询开始...");
        BaseResult baseResult = new BaseResult();
        if (!checkPageParam(params)) {
            baseResult.setCode(ResultCode.LACK_PARAM_CODE.getCode());
            baseResult.setMsg(Constant.SUCCESS_MSG);
            log.info("友链分页查询失败,缺少分页参数...");
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        List<Links> labelList = linksService.getLinksPage(params);
        PageInfo<Links> pageInfo = new PageInfo<>(labelList);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("友链分页查询成功...");
        return baseResult;

    }

    /**
     * 友链添加
     * @param links
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @OLog(module = "友链管理", businessType = OperationType.INSERT)
    @ResponseBody
    public BaseResult save(Links links) {
        log.info("友链新增操作开始...");
        try {
            Links links1 = linksService.isExistLinks(links);
            if (links1 != null) {
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "友链已存在");
            }
            linksService.saveLinks(links);
            return new BaseResult();
        }catch (Exception e){
            log.error("友链新增操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @OLog(module = "友链管理", businessType = OperationType.OTHER)
    @ResponseBody
    public BaseResult info(@PathVariable("id") String id) {
        log.info("友链详情查询操作开始...");
        BaseResult baseResult = new BaseResult();
        Links links = linksService.selectById(id);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(links);
        log.info("友链详情查询操作成功...");
        return baseResult;
    }

    /**
     * 友链修改
     * @param links
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OLog(module = "友链管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult update(Links links) {
        log.info("友链编辑操作开始...");
        try {
            linksService.updateLinks(links);
            return new BaseResult();
        }catch (Exception e){
            log.error("友链编辑操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 友链删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @OLog(module = "友链管理", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("友链删除操作开始...");
        try {
            linksService.deleteBatch(ids);
            return new BaseResult();
        }catch (Exception e){
            log.error("友链删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 导出友链列表
     * @param ids 友链ids
     * @return BaseResult excel文件名
     */
    @PostMapping("/export")
    @OLog(module = "友链管理", businessType = OperationType.EXPORT)
    @ResponseBody
    public BaseResult export(String[] ids, HttpServletResponse response) {
        log.info("友链导出操作开始...");
        try {
            List<Links> linksList = linksService.selectLinksByIds(ids);
            ExcelUtil<Links> util = new ExcelUtil<>(Links.class);
            BaseResult baseResult = util.exportExcel(linksList, "linksList", response);
            log.info("友链导出操作成功...");
            return baseResult;
        }catch (Exception e){
            log.error("友链导出操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
}
