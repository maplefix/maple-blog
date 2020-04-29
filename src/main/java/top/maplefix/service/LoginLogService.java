package top.maplefix.service;

import top.maplefix.model.LoginLog;

import java.util.List;

/**
 * @author : Maple
 * @description : 登录日志接口
 * @date : 2020/2/12 22:57
 */
public interface LoginLogService {

    /**
     * 新增系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    void insertLoginLog(LoginLog loginLog);

    /**
     * 查询系统登录日志集合
     *
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
    List<LoginLog> selectLoginLogList(LoginLog loginLog);

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    int deleteLoginLogByIds(String ids);

    /**
     * 清空系统登录日志
     */
    void cleanLoginLog();
}
