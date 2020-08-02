package top.maplefix.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.maplefix.constant.JobConstant;
import top.maplefix.exception.CustomException;
import top.maplefix.model.Job;

import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Maple
 * @description quartz管理
 * @date 2020/2/1 13:23
 */
@Component
@Slf4j
public class QuartzManage {

    private static final String JOB_NAME = "TASK_";

    @Autowired
    Scheduler scheduler;

    /**
     * 添加任务
     *
     * @param job
     */
    public void addJob(Job job) {
        try {
            log.info("开始创建任务{}", job.getJobName());
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class).
                    withIdentity(JOB_NAME + job.getId()).build();

            //通过触发器名和cron 表达式创建 Trigger
            Trigger cronTrigger = newTrigger()
                    .withIdentity(JOB_NAME + job.getId())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                    .build();

            cronTrigger.getJobDataMap().put(Job.JOB_KEY, job);

            //重置启动时间
            ((CronTriggerImpl) cronTrigger).setStartTime(new Date());

            //执行定时任务
            scheduler.scheduleJob(jobDetail, cronTrigger);

            // 暂停任务
            if (JobConstant.NORMAL != job.getStatus()) {
                pauseJob(job);
            }
        } catch (Exception e) {
            log.error("创建定时任务失败", e);
            throw new CustomException("创建定时任务失败");
        }
    }

    /**
     * 更新job cron表达式
     */
    public void updateJobCron(Job job) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + job.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(job);
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置启动时间
            ((CronTriggerImpl) trigger).setStartTime(new Date());
            trigger.getJobDataMap().put(Job.JOB_KEY, job);

            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (JobConstant.NORMAL != job.getStatus()) {
                pauseJob(job);
            }
        } catch (Exception e) {
            log.error("更新定时任务失败", e);
            throw new CustomException("更新定时任务失败");
        }

    }

    /**
     * 删除一个job
     */
    public void deleteJob(Job job) {
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + job.getId());
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            log.error("删除定时任务失败", e);
            throw new CustomException("删除定时任务失败");
        }
    }

    /**
     * 恢复一个job
     */
    public void resumeJob(Job job) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + job.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(job);
            }
            JobKey jobKey = JobKey.jobKey(JOB_NAME + job.getId());
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            log.error("恢复定时任务失败", e);
            throw new CustomException("恢复定时任务失败");
        }
    }

    /**
     * 立即执行job
     */
    public void runAJobNow(Job job) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + job.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(job);
            }
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(Job.JOB_KEY, job);
            JobKey jobKey = JobKey.jobKey(JOB_NAME + job.getId());
            scheduler.triggerJob(jobKey, dataMap);
        } catch (Exception e) {
            log.error("定时任务执行失败", e);
            throw new CustomException("定时任务执行失败");
        }
    }

    /**
     * 暂停一个job
     *
     * @param job /
     */
    public void pauseJob(Job job) {
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + job.getId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            log.error("定时任务暂停失败", e);
            throw new CustomException("定时任务暂停失败");
        }
    }
}
