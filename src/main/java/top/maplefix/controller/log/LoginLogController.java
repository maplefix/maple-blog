package top.maplefix.controller.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.model.LoginLog;
import top.maplefix.service.LoginLogService;
import top.maplefix.utils.SecurityUtils;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 登录日志控制器
 * @date : 2020/2/12 16:52
 */
@RestController
@RequestMapping("/log/loginLog")
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    @PreAuthorize("@permissionService.hasPermission('monitor:loginLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(LoginLog loginLog) {
        startPage();
        List<LoginLog> list = loginLogService.selectLoginLogList(loginLog);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:loginLog:query')")
    @GetMapping()
    public TableDataInfo queryCurrentUserLoginLog(LoginLog loginLog) {
        startPage();
        loginLog.setUserName(SecurityUtils.getUsername());
        List<LoginLog> list = loginLogService.selectLoginLogList(loginLog);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:loginLog:remove')")
    @DeleteMapping("{ids}")
    public BaseResult deleteLoginLog(@PathVariable String ids) {
        return toResult(loginLogService.deleteLoginLogByIds(ids));
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:loginLog:remove')")
    @DeleteMapping("/clean")
    public BaseResult cleanLoginLog() {
        loginLogService.cleanLoginLog();
        return BaseResult.success();
    }
}