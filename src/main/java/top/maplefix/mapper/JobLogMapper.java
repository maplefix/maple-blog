package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import top.maplefix.model.JobLog;

import java.util.List;

/**
 * @author Maple
 * @description 定时任务日志
 * @date 2020/2/1 13:30
 */
public interface JobLogMapper {

    /**
     * 插入Job log
     *
     * @param jobLog log
     * @return 受影响的行数
     */
    int insertJobLog(JobLog jobLog);

    /**
     * 根据条件查询Log
     *
     * @param jobLog 条件
     * @return list
     */
    List<JobLog> selectJobLogList(JobLog jobLog);

    /**
     * 根据id删除数据
     *
     * @param ids      id数组
     * @param username 操作者
     * @return 受影响的行数
     */
    int deleteJobLogByIds(@Param("ids") String[] ids, @Param("username") String username);

    /**
     * 清空日志
     *
     */
    void cleanJobLog();

    /**
     * select quartz log by id
     *
     * @param id id
     * @return quartzLog
     */
    JobLog selectJobLogById(String id);
}
