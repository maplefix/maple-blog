package top.maplefix.controller.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.model.Job;
import top.maplefix.service.JobService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author Maple
 * @description 定时任务controller
 * @date 2020/3/20 11:04
 */
@RestController
@RequestMapping("/tool/job")
public class JobController extends BaseController {
    
    @Autowired
    private JobService jobService;

    @GetMapping("/list")
    @PreAuthorize("@permissionService.hasPermission('tool:job:list')")
    public TableDataInfo list(Job job) {
        startPage();
        List<Job> jobList = jobService.selectJobList(job);
        return getDataTable(jobList);
    }

    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('tool:job:add')")
    public BaseResult add(@Validated @RequestBody Job job) {
        return toResult(jobService.insertJob(job));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@permissionService.hasPermission('tool:job:query')")
    public BaseResult add(@PathVariable String id) {
        return BaseResult.success(jobService.selectJobById(id));
    }

    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('tool:job:edit')")
    public BaseResult edit(@RequestBody Job job) {
        return toResult(jobService.updateJob(job));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("@permissionService.hasPermission('tool:job:remove')")
    public BaseResult delete(@PathVariable String id) {
        return toResult(jobService.deleteJob(id));
    }

    @PutMapping("/exe/{id}")
    @PreAuthorize("@permissionService.hasPermission('tool:job:exec')")
    public BaseResult execute(@PathVariable String id) {
        jobService.executeJobById(id);
        return BaseResult.success();
    }

    @PutMapping("/status/{id}")
    @PreAuthorize("@permissionService.hasPermission('tool:job:changeStatus')")
    public BaseResult updateJobStatus(@PathVariable String id) {
        return toResult(jobService.updateJobStatus(id));
    }
}
