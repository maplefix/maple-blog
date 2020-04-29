package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.VisitLogMapper;
import top.maplefix.model.VisitLog;
import top.maplefix.service.VisitLogService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.SecurityUtils;

import java.util.List;

/**
 * @author : Maple
 * @description : 访问日志接口实现类
 * @date : 2020/2/24 23:01
 */
@Service
public class VisitLogServiceImpl implements VisitLogService {

    @Autowired
    private VisitLogMapper visitLogMapper;

    @Override
    public List<VisitLog> selectVisitLogList(VisitLog visitLog) {
        return visitLogMapper.selectVisitLogList(visitLog);
    }

    @Override
    public int deleteVisitLogByIds(String ids) {
        return visitLogMapper.deleteVisitLogByIds(ConvertUtils.toStrArray(ids));
    }

    @Override
    public void cleanVisitLog() {
        String username = SecurityUtils.getUsername();
        visitLogMapper.cleanVisitLog(username);
    }

    @Override
    public int insertVisitLog(VisitLog visitLog) {
        return visitLogMapper.insertVisitLog(visitLog);
    }

}
