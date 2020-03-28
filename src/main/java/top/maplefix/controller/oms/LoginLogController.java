package top.maplefix.controller.oms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
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
@Controller
@RequestMapping("/loginLog")
@Slf4j
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
        loginLog.setLoginName(SecurityUtils.getUsername());
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
