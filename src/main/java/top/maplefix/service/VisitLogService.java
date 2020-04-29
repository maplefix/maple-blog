package top.maplefix.service;

import top.maplefix.model.VisitLog;

import java.util.List;

/**
 * @author : Maple
 * @description : 访问日志接口
 * @date : 2020/2/20 23:01
 */
public interface VisitLogService {

    /**
     * 根据查询条件获取List
     *
     * @param visitLog 查询条件
     * @return list
     */
    List<VisitLog> selectVisitLogList(VisitLog visitLog);

    /**
     * 根据Id删除所有的log
     *
     * @param ids id集合
     * @return 受影响的行数
     */
    int deleteVisitLogByIds(String ids);

    /**
     * 清除所有的log
     */
    void cleanVisitLog();

    /**
     * 插入log
     *
     * @param visitLog log
     * @return 受影响的行数
     */
    int insertVisitLog(VisitLog visitLog);
}
