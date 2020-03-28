package top.maplefix.controller.oms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
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
@RequestMapping("/job")
public class JobController extends BaseController {
    
    @Autowired
    private JobService jobService;

    @GetMapping("/list")
    public TableDataInfo list(Job job) {
        startPage();
        List<Job> jobList = jobService.selectJobList(job);
        return getDataTable(jobList);
    }

    @PostMapping()
    public BaseResult add(@Validated @RequestBody Job job) {
        return toResult(jobService.insertJob(job));
    }

    @GetMapping("/{id}")
    public BaseResult add(@PathVariable String id) {
        return BaseResult.success(jobService.selectJobById(id));
    }

    @PutMapping()
    public BaseResult edit(@RequestBody Job job) {
        return toResult(jobService.updateJob(job));
    }

    @DeleteMapping("{id}")
    public BaseResult delete(@PathVariable String id) {
        return toResult(jobService.deleteJob(id));
    }

    @PutMapping("/exe/{id}")
    public BaseResult execute(@PathVariable String id) {
        jobService.executeJobById(id);
        return BaseResult.success();
    }

    @PutMapping("/status/{id}")
    public BaseResult updateJobStatus(@PathVariable String id) {
        return toResult(jobService.updateJobStatus(id));
    }
}
