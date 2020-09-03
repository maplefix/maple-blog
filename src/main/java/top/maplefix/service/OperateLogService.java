package top.maplefix.service;

import top.maplefix.model.OperateLog;

import java.util.List;

/**
 * @author : Maple
 * @description : 操作日志接口
 * @date : 2020/1/24 22:58
 */
public interface OperateLogService {

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    void insertOperateLog(OperateLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    List<OperateLog> selectOperateLogList(OperateLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteOperateLogByIds(String ids);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    OperateLog selectOperateLogById(Long operId);

    /**
     * 清空操作日志
     */
    void cleanOperateLog();
}
