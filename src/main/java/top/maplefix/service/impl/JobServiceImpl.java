package top.maplefix.service.impl;

import org.springframework.stereotype.Service;
import top.maplefix.model.Job;
import top.maplefix.service.JobService;

import java.util.List;

/**
 * @author Maple
 * @description 定时任务service实现
 * @date 2020/2/1 13:16
 */
@Service
public class JobServiceImpl implements JobService {
    @Override
    public int insertJob(Job job) {
        return 0;
    }

    @Override
    public int updateJob(Job job) {
        return 0;
    }

    @Override
    public int deleteJob(String jobId) {
        return 0;
    }

    @Override
    public void executeJobById(String jobId) {

    }

    @Override
    public int updateJobStatus(String jobId) {
        return 0;
    }

    @Override
    public List<Job> selectJobList(Job job) {
        return null;
    }

    @Override
    public Job selectJobById(String jobId) {
        return null;
    }

    @Override
    public List<Job> selectRunningJobList() {
        return null;
    }
}
