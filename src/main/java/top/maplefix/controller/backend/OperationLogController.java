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
import top.maplefix.model.OperationLog;
import top.maplefix.service.IOperationLogService;
import top.maplefix.utils.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 操作日志控制类
 * @date : Created in 2019/7/28 16:37
 * @editor:
 * @version: v2.1
 */
@Controller
@RequestMapping("/api/admin/operationLog")
@Slf4j
public class OperationLogController extends BaseController {

    @Autowired
    private IOperationLogService operationLogService;

    /**
     * 操作日志页面
     * @param request
     * @return
     */
    @GetMapping({"","/"})
    public String operationLogPage(HttpServletRequest request) {
        request.setAttribute("path", "operationLog");
        return "admin/operation-log";
    }

    /**
     * 查询操作日志列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("操作日志分页查询开始...");
        BaseResult baseResult = new BaseResult();
        if (!checkPageParam(params)) {
            baseResult.setCode(ResultCode.LACK_PARAM_CODE.getCode());
            baseResult.setMsg(Constant.SUCCESS_MSG);
            log.info("操作日志分页查询失败,缺少分页参数...");
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        List<OperationLog> OperationLogList = operationLogService.getOperationLogPage(params);
        PageInfo<OperationLog> pageInfo = new PageInfo<>(OperationLogList);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("操作日志分页查询成功...");
        return baseResult;

    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @OLog(module = "操作日志", businessType = OperationType.OTHER)
    @ResponseBody
    public BaseResult info(@PathVariable("id") String id) {
        log.info("操作日志详情查询操作开始...");
        BaseResult baseResult = new BaseResult();
        OperationLog OperationLog = operationLogService.selectById(id);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(OperationLog);
        log.info("操作日志详情查询操作成功...");
        return baseResult;
    }

    /**
     * 操作日志修改
     * @param operationLog
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OLog(module = "操作日志", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult update(OperationLog operationLog) {
        log.info("操作日志编辑操作开始...");
        try {
            operationLogService.updateOperationLog(operationLog);
            log.info("操作日志编辑操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("操作日志编辑操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 操作日志删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @OLog(module = "操作日志", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("操作日志删除操作开始...");
        try {
            operationLogService.deleteBatch(ids);
            log.info("操作日志删除操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("操作日志删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 导出操作日志列表
     * @param ids 登录日志ids
     * @return BaseResult excel文件名
     */
    @PostMapping("/export")
    @OLog(module = "操作日志", businessType = OperationType.EXPORT)
    @ResponseBody
    public BaseResult export(String[] ids, HttpServletResponse response) {
        log.info("操作日志导出操作开始...");
        try {
            List<OperationLog> operationLogList = operationLogService.selectOperationLogByIds(ids);
            ExcelUtil<OperationLog> util = new ExcelUtil<>(OperationLog.class);
            BaseResult baseResult = util.exportExcel(operationLogList, "operationLogList", response);
            log.info("操作日志导出操作成功...");
            return baseResult;
        }catch (Exception e){
            log.error("操作日志导出操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
}
