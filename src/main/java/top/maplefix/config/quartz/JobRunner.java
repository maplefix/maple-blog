package top.maplefix.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import top.maplefix.model.Job;
import top.maplefix.service.JobService;

import java.util.List;

/**
 * @author Maple
 * @description 定时任务runner,项目启动时从数据库加载定时任务运行
 * @date 2020/1/18 15:31
 */
@Slf4j
public class JobRunner implements ApplicationRunner {

    @Autowired
    QuartzManage quartzManage;
    @Autowired
    JobService jobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start task...");
        List<Job> quartzJobList = jobService.selectRunningJobList();
        quartzJobList.forEach(quartzManage::addJob);
        log.info("end inject task,the size of task {}", quartzJobList.size());
    }
}
