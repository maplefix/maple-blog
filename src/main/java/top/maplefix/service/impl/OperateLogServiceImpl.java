package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.OperateLogMapper;
import top.maplefix.model.OperateLog;
import top.maplefix.service.OperateLogService;
import top.maplefix.utils.ConvertUtils;

import java.util.List;

/**
 * @author : Maple
 * @description : 操作日志接口实现类
 * @date : 2020/1/24 22:59 
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 新增操作日志
     *
     * @param operateLog 操作日志对象
     */
    @Override
    public void insertOperateLog(OperateLog operateLog) {
        operateLogMapper.insertOperateLog(operateLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operateLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<OperateLog> selectOperateLogList(OperateLog operateLog) {
        return operateLogMapper.selectOperateLogList(operateLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteOperateLogByIds(String ids) {
        return operateLogMapper.deleteOperateLogByIds(ConvertUtils.toStrArray(ids));
    }

    /**
     * 查询操作日志详细
     *
     * @param id 操作ID
     * @return 操作日志对象
     */
    @Override
    public OperateLog selectOperateLogById(String id) {
        return operateLogMapper.selectOperateLogById(id);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperateLog() {
        operateLogMapper.cleanOperateLog();
    }
}
