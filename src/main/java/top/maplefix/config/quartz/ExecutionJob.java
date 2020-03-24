package top.maplefix.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.maplefix.constant.Constant;
import top.maplefix.constant.JobConstant;
import top.maplefix.model.Job;
import top.maplefix.model.JobLog;
import top.maplefix.service.JobLogService;
import top.maplefix.service.JobService;
import top.maplefix.utils.SpringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.Future;

/**
 * @author Maple
 * @description 任务执行
 * @date 2020/2/1 13:27
 */
@Component
@Slf4j
public class ExecutionJob extends QuartzJobBean {

    ThreadPoolTaskExecutor threadPoolTaskExecutor = SpringUtils.getBean("threadPoolTaskExecutor");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Job quartzJob = (Job) context.getMergedJobDataMap().get(Job.JOB_KEY);
        // 获取spring bean
        JobService quartzJobService = SpringUtils.getBean(JobService.class);
        JobLogService quartzJobLogService = SpringUtils.getBean(JobLogService.class);

        JobLog quartzJobLog = new JobLog();
        quartzJobLog.setJobName(quartzJob.getJobName());
        quartzJobLog.setBeanName(quartzJob.getBeanName());
        quartzJobLog.setMethodName(quartzJob.getMethodName());
        quartzJobLog.setParams(quartzJob.getParams());
        quartzJobLog.setCronExpression(quartzJob.getCronExpression());

        long startTime = System.currentTimeMillis();
        try {
            // 执行任务
            log.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(), quartzJob.getParams());
            Future<Object> future = threadPoolTaskExecutor.submit(task);
            Object result = future.get();
            log.info("任务返回值:{}", result);
            quartzJobLog.setResult(result.toString());
            long times = System.currentTimeMillis() - startTime;
            quartzJobLog.setCost(times);
            // 任务状态
            quartzJobLog.setStatus(Constant.SUCCESS);
            log.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), times);
        } catch (Exception e) {
            log.error("任务执行失败，任务名称：{}" + quartzJob.getJobName(), e);
            long times = System.currentTimeMillis() - startTime;
            quartzJobLog.setCost(times);
            quartzJobLog.setStatus(Constant.FAILED);
            quartzJobLog.setExceptionMsg(getStackTrace(e));
            //设置为暂停状态
            quartzJob.setStatus(JobConstant.PAUSE);
            //更新状态
            quartzJobService.updateJob(quartzJob);
        } finally {
            quartzJobLogService.insertJobLog(quartzJobLog);
        }
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}