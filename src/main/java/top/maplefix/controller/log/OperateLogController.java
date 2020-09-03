package top.maplefix.controller.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.model.OperateLog;
import top.maplefix.service.OperateLogService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 操作日志控制类
 * @date : 2020/1/28 16:37
 */
@RestController
@RequestMapping("/log/operateLog")
public class OperateLogController extends BaseController {

    @Autowired
    private OperateLogService operateLogService;

    @PreAuthorize("@permissionService.hasPermission('monitor:operateLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(OperateLog operateLog) {
        startPage();
        List<OperateLog> list = operateLogService.selectOperateLogList(operateLog);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:operateLog:remove')")
    @DeleteMapping("/{ids}")
    public BaseResult deleteQuartzJobLogByIds(@PathVariable String ids) {
        return toResult(operateLogService.deleteOperateLogByIds(ids));
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:operateLog:remove')")
    @DeleteMapping("/clean")
    public BaseResult cleanQuartzJobLog() {
        operateLogService.cleanOperateLog();
        return BaseResult.success();
    }
}
