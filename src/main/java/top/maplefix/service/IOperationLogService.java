package top.maplefix.service;

import top.maplefix.model.OperationLog;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 操作日志接口
 * @date : Created in 2019/7/24 22:58
 * @version : v2.1
 */
public interface IOperationLogService {

    /**
     * 分页查询操作日志列表
     * @param params
     * @return
     */
    List<OperationLog> getOperationLogPage(Map<String, Object> params);


    /**
     * 根据id查询
     * @param operationLogId
     * @return
     */
    OperationLog selectById(String operationLogId);

    /**
     * 保存操作日志
     * @param operationLog
     */
    void saveOperationLog(OperationLog operationLog);

    /**
     * 修改操作日志
     * @param operationLog
     */
    void updateOperationLog(OperationLog operationLog);

    /**
     * 根据id批量删除
     * @param operationLogIds
     */
    void deleteBatch(String[] operationLogIds);

    /**
     * 根据id查操作日志列表
     * @param ids 操作日志id数据
     * @return list
     */
    List<OperationLog> selectOperationLogByIds(String[] ids);
}
