package top.maplefix.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.JobLogMapper;
import top.maplefix.model.JobLog;
import top.maplefix.service.JobLogService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.SecurityUtils;

import java.util.List;

/**
 * @author Maple
 * @description 定时任务日志
 * @date 2020/2/1 13:35
 */
@Slf4j
@Service
public class JobLogServiceImpl implements JobLogService {

    @Autowired
    JobLogMapper jobLogMapper;

    @Override
    public int insertJobLog(JobLog JobLog) {
        return jobLogMapper.insertJobLog(JobLog);
    }

    @Override
    public List<JobLog> selectJobLogList(JobLog JobLog) {
        return jobLogMapper.selectJobLogList(JobLog);
    }

    @Override
    public int deleteJobLogByIds(String ids) {
        String username = SecurityUtils.getUsername();
        return jobLogMapper.deleteJobLogByIds(ConvertUtils.toStrArray(ids), username);
    }

    @Override
    public void cleanJobLog() {
        jobLogMapper.cleanJobLog();
    }

    @Override
    public JobLog selectJobLogById(String id) {
        return jobLogMapper.selectJobLogById(id);
    }
}
