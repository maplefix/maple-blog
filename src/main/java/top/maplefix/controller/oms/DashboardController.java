package top.maplefix.controller.oms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.service.DashboardService;
import top.maplefix.vo.LineChartData;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 首页图标数据接口
 * @date : 2020/1/15 17:46
 */
@RestController
@RequestMapping("/dashboard")
@Slf4j
public class DashboardController extends BaseController {
    @Autowired
    private DashboardService dashboardService;

    /**
     * 面板分组数据查询
     * @return
     */
    @GetMapping("/panelGroup")
    public BaseResult getPanelGroupData() {
        Map<String, Long> panelGroupData = dashboardService.getPanelGroupData();
        return BaseResult.success(panelGroupData);
    }

    /**
     * 折线图数据查询
     * @param type 类型
     * @return BaseResult
     */
    @GetMapping("lineChartData/{type}")
    public BaseResult getLineChartData(@PathVariable String type) {
        LineChartData<Long> lineChartData = dashboardService.getLineChartData(type);
        return BaseResult.success(lineChartData);
    }

    /**
     * 爬虫数据加载
     * @return BaseResult
     */
    @GetMapping("/spiderData")
    public BaseResult getSpiderData() {
        List<Map<String, Long>> result = dashboardService.getSpiderData();
        return BaseResult.success(result);
    }

    /**
     * 访客日志分页加载
     * @return TableDataInfo
     */
    @GetMapping("/visitLog")
    public TableDataInfo visitLogDataInfo() {
        startPage();
        List<String> visitLogList = dashboardService.getVisitLogStringList();
        return getDataTable(visitLogList);
    }

    /**
     * 登陆日志分页查询
     * @return TableDataInfo
     */
    @GetMapping("/loginLog")
    public TableDataInfo loginLogDataInfo() {
        startPage();
        List<String> visitLogList = dashboardService.getLoginLogStringList();
        return getDataTable(visitLogList);
    }
    /**
     * 操作日志分页查询
     * @return TableDataInfo
     */
    @GetMapping("/operateLog")
    public TableDataInfo operateLogDataInfo() {
        startPage();
        List<String> visitLogList = dashboardService.getOperateLogStringList();
        return getDataTable(visitLogList);
    }
    /**
     * 任务日志分页查询
     * @return TableDataInfo
     */
    @GetMapping("/taskLog")
    public TableDataInfo taskLogDataInfo() {
        startPage();
        List<String> visitLogList = dashboardService.getTaskLogStringList();
        return getDataTable(visitLogList);
    }
}
