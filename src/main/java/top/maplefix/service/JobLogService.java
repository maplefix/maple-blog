package top.maplefix.service;

import top.maplefix.model.JobLog;

import java.util.List;

/**
 * @author Maple
 * @description job
 * @date 2020/2/1 13:34
 */
public interface JobLogService {

    /**
     * 插入Job log
     *
     * @param jobLog log
     */
    int insertJobLog(JobLog jobLog);

    /**
     * 获取JobLog
     *
     * @param jobLog 查询条件
     * @return list
     */
    List<JobLog> selectJobLogList(JobLog jobLog);

    /**
     * 根据id删除日志
     *
     * @param ids id集合
     * @return 受影响的行数
     */
    int deleteJobLogByIds(String ids);

    /**
     * 清空日志
     */
    void cleanJobLog();

    /**
     * select quartz job log by id
     *
     * @param id id
     * @return quartzLog
     */
    JobLog selectJobLogById(String id);
}
