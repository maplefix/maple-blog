package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.LoginLogMapper;
import top.maplefix.model.LoginLog;
import top.maplefix.redis.RedisCacheService;
import top.maplefix.service.LoginLogService;
import top.maplefix.utils.ConvertUtils;

import java.util.List;

/**
 * @author : Maple
 * @description : 登录日志操作接口实现类
 * @date : 2020/2/24 21:47
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    RedisCacheService redisCacheService;

    /**
     * 新增系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    @Override
    public void insertLoginLog(LoginLog loginLog) {
        loginLogMapper.insertLoginLog(loginLog);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<LoginLog> selectLoginLogList(LoginLog loginLog) {
        return loginLogMapper.selectLoginLogList(loginLog);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteLoginLogByIds(String ids) {
        return loginLogMapper.deleteLoginLogByIds(ConvertUtils.toStrArray(ids));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginLog() {
        loginLogMapper.cleanLoginLog();
    }
}
