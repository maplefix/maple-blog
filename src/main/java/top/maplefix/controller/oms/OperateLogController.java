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
import top.maplefix.model.OperateLog;
import top.maplefix.service.OperateLogService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 操作日志控制类
 * @date : 2020/1/28 16:37
 */
@Controller
@RequestMapping("/operateLog")
@Slf4j
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
