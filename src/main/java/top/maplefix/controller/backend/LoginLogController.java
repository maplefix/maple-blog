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
import top.maplefix.model.LoginLog;
import top.maplefix.service.ILoginLogService;
import top.maplefix.utils.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 登录日志控制器
 * @date : Created in 2019/9/12 16:52
 * @editor:
 * @version: v2.1
 */
@Controller
@RequestMapping("/api/admin/loginLog")
@Slf4j
public class LoginLogController extends BaseController {
    
    @Autowired
    private ILoginLogService loginLogService;

    /**
     * 登录日志页面
     * @param request
     * @return
     */
    @GetMapping({"","/"})
    public String loginLogPage(HttpServletRequest request) {
        request.setAttribute("path", "loginLog");
        return "admin/login-log";
    }

    /**
     * 查询登录日志列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("登录日志分页查询开始...");
        BaseResult baseResult = new BaseResult();
        if (!checkPageParam(params)) {
            baseResult.setCode(ResultCode.LACK_PARAM_CODE.getCode());
            baseResult.setMsg(Constant.SUCCESS_MSG);
            log.info("登录日志分页查询失败,缺少分页参数...");
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        List<LoginLog> loginLogList = loginLogService.getLoginLogPage(params);
        PageInfo<LoginLog> pageInfo = new PageInfo<>(loginLogList);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("登录日志分页查询成功...");
        return baseResult;

    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @OLog(module = "登录日志", businessType = OperationType.OTHER)
    @ResponseBody
    public BaseResult info(@PathVariable("id") String id) {
        log.info("登录日志详情查询操作开始...");
        BaseResult baseResult = new BaseResult();
        LoginLog loginLog = loginLogService.selectById(id);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(loginLog);
        log.info("登录日志详情查询操作成功...");
        return baseResult;
    }

    /**
     * 登录日志修改
     * @param loginLog
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OLog(module = "登录日志", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult update(LoginLog loginLog) {
        log.info("登录日志编辑操作开始...");
        try {
            loginLogService.updateLoginLog(loginLog);
            log.info("登录日志编辑操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("登录日志编辑操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 登录日志删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @OLog(module = "登录日志", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("登录日志删除操作开始...");
        try {
            loginLogService.deleteBatch(ids);
            log.info("登录日志删除操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("登录日志删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 导出登陆日志列表
     * @param ids 登录日志ids
     * @return BaseResult
     */
    @PostMapping("/export")
    @OLog(module = "登录日志", businessType = OperationType.EXPORT)
    @ResponseBody
    public BaseResult export(String[] ids, HttpServletResponse response) {
        log.info("登录日志导出操作开始...");
        try {
            List<LoginLog> loginLogList = loginLogService.selectLoginLogByIds(ids);
            ExcelUtil<LoginLog> util = new ExcelUtil<>(LoginLog.class);
            BaseResult baseResult = util.exportExcel(loginLogList, "loginLogList", response);
            log.info("登录日志导出操作成功...");
            return baseResult;
        }catch (Exception e){
            log.error("登录日志导出操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
}
