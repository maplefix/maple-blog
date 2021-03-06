package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import top.maplefix.model.Job;

import java.util.List;

/**
 * @author Maple
 * @description 定时任务
 * @date 2020/2/1 13:30
 */
public interface JobMapper {
    /**
     * 添加Job
     *
     * @param job job
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
     * 根据Id查询Job
     *
     * @param id id
     * @return Job
     */
    Job selectJobById(Long id);

    /**
     * 根据Id删除Job
     *
     * @param id id
     * @return 受影响的行数
     */
    int deleteJobById(@Param("id") Long id);

    /**
     * 查询Job list
     *
     * @param job job
     * @return list
     */
    List<Job> selectJobList(Job job);

    /**
     * 获取运行状态的job
     *
     * @return list
     */
    List<Job> selectRunningJobList();
}
