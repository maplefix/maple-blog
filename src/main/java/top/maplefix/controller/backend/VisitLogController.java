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
import top.maplefix.model.VisitLog;
import top.maplefix.service.IVisitLogService;
import top.maplefix.utils.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 访问日志控制类
 * @date : Created in 2019/7/28 16:38
 * @editor:
 * @version: v2.1
 */
@Controller
@RequestMapping("/api/admin/visitLog")
@Slf4j
public class VisitLogController extends BaseController {

    @Autowired
    private IVisitLogService visitLogService;

    /**
     * 访问日志页面
     * @param request
     * @return
     */
    @GetMapping({"","/"})
    public String visitLogPage(HttpServletRequest request) {
        request.setAttribute("path", "visitLog");
        return "admin/visit-log";
    }

    /**
     * 查询访问日志列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("访问日志分页查询开始...");
        BaseResult baseResult = new BaseResult();
        if (!checkPageParam(params)) {
            baseResult.setCode(ResultCode.LACK_PARAM_CODE.getCode());
            baseResult.setMsg(Constant.SUCCESS_MSG);
            log.info("访问日志分页查询失败,缺少分页参数...");
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        List<VisitLog> VisitLogList = visitLogService.getVisitLogPage(params);
        PageInfo<VisitLog> pageInfo = new PageInfo<>(VisitLogList);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("访问日志分页查询成功...");
        return baseResult;

    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @OLog(module = "访问日志", businessType = OperationType.OTHER)
    @ResponseBody
    public BaseResult info(@PathVariable("id") String id) {
        log.info("访问日志详情查询操作开始...");
        BaseResult baseResult = new BaseResult();
        VisitLog VisitLog = visitLogService.selectById(id);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(VisitLog);
        log.info("访问日志详情查询操作成功...");
        return baseResult;
    }

    /**
     * 访问日志修改
     * @param VisitLog
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OLog(module = "访问日志", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult update(VisitLog VisitLog) {
        log.info("访问日志编辑操作开始...");
        try {
            visitLogService.updateVisitLog(VisitLog);
            log.info("访问日志编辑操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("访问日志编辑操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 访问日志删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @OLog(module = "访问日志", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("访问日志删除操作开始...");
        try {
            visitLogService.deleteBatch(ids);
            log.info("访问日志删除操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("访问日志删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 导出访问日志列表
     * @param ids 访问日志ids
     * @return BaseResult excel文件名
     */
    @PostMapping("/export")
    @OLog(module = "访问日志", businessType = OperationType.EXPORT)
    @ResponseBody
    public BaseResult export(String[] ids, HttpServletResponse response) {
        log.info("访问日志导出操作开始...");
        try {
            List<VisitLog> visitLogList = visitLogService.selectVisitLogByIds(ids);
            ExcelUtil<VisitLog> util = new ExcelUtil<>(VisitLog.class);
            BaseResult baseResult = util.exportExcel(visitLogList, "visitLogList", response);
            log.info("访问日志导出操作成功...");
            return baseResult;
        }catch (Exception e){
            log.error("访问日志导出操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
}
