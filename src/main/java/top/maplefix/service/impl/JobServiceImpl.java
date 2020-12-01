package top.maplefix.service.impl;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.config.quartz.QuartzManage;
import top.maplefix.exception.CustomException;
import top.maplefix.mapper.JobMapper;
import top.maplefix.model.Job;
import top.maplefix.service.JobService;
import top.maplefix.utils.DateUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Maple
 * @description 定时任务service实现
 * @date 2020/2/1 13:16
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobMapper jobMapper;

    @Autowired
    QuartzManage quartzManage;

    @Override
    public int insertJob(Job job) {
        if (!CronExpression.isValidExpression(job.getCronExpression())) {
            throw new CustomException("Cron表达式错误");
        }
        quartzManage.addJob(job);
        job.setCreateDate(DateUtils.getTime());
        return jobMapper.insertJob(job);
    }

    @Override
    public int updateJob(Job job) {
        if (!CronExpression.isValidExpression(job.getCronExpression())) {
            throw new CustomException("Cron表达式错误");
        }
        quartzManage.updateJobCron(job);
        return jobMapper.updateJob(job);
    }

    @Override
    public int deleteJob(Long id) {
        Job job = jobMapper.selectJobById(id);
        if (Objects.isNull(job)) {
            throw new CustomException("当前任务不存在");
        }
        quartzManage.deleteJob(job);
        return jobMapper.deleteJobById(id);
    }

    @Override
    public void executeJobById(Long id) {
        Job job = jobMapper.selectJobById(id);
        if (Objects.isNull(job)) {
            throw new CustomException("当前任务不存在");
        }
        quartzManage.runAJobNow(job);
    }

    @Override
    public int updateJobStatus(Long id) {
        Job job = jobMapper.selectJobById(id);
        if (Objects.isNull(job)) {
            throw new CustomException("当前任务不存在");
        }
        //如果当前为运行状态
        if (job.getStatus()) {
            quartzManage.pauseJob(job);
        } else {
            quartzManage.resumeJob(job);
        }
        job.setStatus(!job.getStatus());
        return jobMapper.updateJob(job);
    }

    @Override
    public List<Job> selectJobList(Job job) {
        return jobMapper.selectJobList(job);
    }

    @Override
    public Job selectJobById(Long id) {
        return jobMapper.selectJobById(id);
    }

    @Override
    public List<Job> selectRunningJobList() {
        return jobMapper.selectRunningJobList();
    }
}
