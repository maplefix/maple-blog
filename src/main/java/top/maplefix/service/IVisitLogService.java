package top.maplefix.service;

import top.maplefix.model.VisitLog;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 访问日志接口
 * @date : Created in 2019/7/24 23:01
 * @version : v1.0
 */
public interface IVisitLogService {

    /**
     * 分页查询访问日志列表
     * @param params
     * @return
     */
    List<VisitLog> getVisitLogPage(Map<String, Object> params);

    /**
     * 根据id查询
     * @param visitLogId
     * @return
     */
    VisitLog selectById(String visitLogId);

    /**
     * 保存访问日志
     * @param visitLog
     */
    void saveVisitLog(VisitLog visitLog);

    /**
     * 修改访问日志
     * @param visitLog
     */
    void updateVisitLog(VisitLog visitLog);

    /**
     * 根据id批量删除
     * @param visitLogIds
     */
    void deleteBatch(String[] visitLogIds);

    /**
     * 根据id查访问日志列表
     * @param ids 访问日志id数据
     * @return list
     */
    List<VisitLog> selectVisitLogByIds(String[] ids);
}
