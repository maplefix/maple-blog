package top.maplefix.service;

import top.maplefix.model.LoginLog;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 登录日志接口
 * @date : Created in 2019/7/24 22:57
 * @version : v2.1
 */
public interface ILoginLogService {

    /**
     * 分页查询登录日志列表
     * @param params
     * @return
     */
    List<LoginLog> getLoginLogPage(Map<String, Object> params);

    /**
     * 根据id查询
     * @param loginLogId
     * @return
     */
    LoginLog selectById(String loginLogId);

    /**
     * 保存登录日志
     * @param loginLog
     */
    void saveLoginLog(LoginLog loginLog);

    /**
     * 修改登录日志
     * @param loginLog
     */
    void updateLoginLog(LoginLog loginLog);

    /**
     * 根据id批量删除
     * @param loginLogIds
     */
    void deleteBatch(String[] loginLogIds);

    /**
     * 根据id查登陆日志列表
     * @param ids 登陆日志id数据
     * @return list
     */
    List<LoginLog> selectLoginLogByIds(String[] ids);
}
