package top.maplefix.controller.oms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.model.JobLog;
import top.maplefix.service.JobLogService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author Maple
 * @description 定时任务日志数据接口
 * @date 2020/3/18 15:47
 */
@RestController
@RequestMapping("/jobLog")
@Slf4j
public class JobLogController extends BaseController {
    
    @Autowired
    private JobLogService jobLogService;

    @GetMapping("/list")
    @PreAuthorize("@permissionService.hasPermission('monitor:loginlLog:list')")
    public TableDataInfo list(JobLog jobLog) {
        startPage();
        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
        return getDataTable(list);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@permissionService.hasPermission('monitor:loginLog:query')")
    public BaseResult query(@PathVariable String id) {
        return BaseResult.success(jobLogService.selectJobLogById(id));
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:Log:remove')")
    @DeleteMapping("/{ids}")
    public BaseResult deleteJobLogByIds(@PathVariable String ids) {
        return toResult(jobLogService.deleteJobLogByIds(ids));
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:Log:remove')")
    @DeleteMapping("/clean")
    public BaseResult cleanJobLog() {
        jobLogService.cleanJobLog();
        return BaseResult.success();
    }
}
