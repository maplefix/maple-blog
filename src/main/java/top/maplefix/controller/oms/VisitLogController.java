package top.maplefix.controller.oms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.model.VisitLog;
import top.maplefix.service.VisitLogService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 访问日志控制类
 * @date : 2020/2/20 21:38
 */
@RestController
@RequestMapping("/visitLog")
@Slf4j
public class VisitLogController extends BaseController {

    @Autowired
    private VisitLogService visitLogService;

    @PreAuthorize("@permissionService.hasPermission('monitor:visitLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisitLog visitLog) {
        startPage();
        List<VisitLog> list = visitLogService.selectVisitLogList(visitLog);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:visitLog:remove')")
    @DeleteMapping("{ids}")
    public BaseResult deleteLoginLog(@PathVariable String ids) {
        return toResult(visitLogService.deleteVisitLogByIds(ids));
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:visitLog:remove')")
    @DeleteMapping("/clean")
    public BaseResult cleanVisitLog() {
        visitLogService.cleanVisitLog();
        return BaseResult.success();
    }
}
