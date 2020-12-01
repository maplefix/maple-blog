package top.maplefix.service;

import top.maplefix.model.Job;

import java.util.List;

/**
 * @author Maple
 * @description 定时任务service接口
 * @date 2020/2/1 13:12
 */
public interface JobService {

    /**
     * 新增定时任务
     *
     * @param job 定时任务
     * @return 受影响的行数
     */
    int insertJob(Job job);

    /**
     * 更新Job
     *
     * @param job job实体
     * @return 受影响的行数
     */
    int updateJob(Job job);

    /**
     * 根据id删除Job
     *
     * @param id id
     * @return 受影响的行数
     */
    int deleteJob(Long id);

    /**
     * 执行Job
     *
     * @param id id
     */
    void executeJobById(Long id);

    /**
     * 更新Job的状态(暂停,运行)
     *
     * @param id job的id
     * @return 受影响的行数
     */
    int updateJobStatus(Long id);

    /**
     * 获取Job list
     *
     * @param job 查询条件实体
     * @return job list
     */
    List<Job> selectJobList(Job job);

    /**
     * 根据id查询Job
     *
     * @param id id
     * @return job
     */
    Job selectJobById(Long id);

    /**
     * 获取运行状态为true的job
     *
     * @return job list
     */
    List<Job> selectRunningJobList();
}
