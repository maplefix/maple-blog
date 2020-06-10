package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.LoginLog;

import java.util.List;

/**
 * @author : Maple
 * @description : 登录日志mapper
 * @date : 2020/2/25 0:32
 */
public interface LoginLogMapper extends Mapper<LoginLog>{

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
     * @return 结果
     */
    int deleteLoginLogByIds(@Param("ids") String[] ids);

    /**
     * f
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLoginLog();

    /**
     * update login log
     *
     * @param loginLog login log
     * @return update count
     */
    int updateLoginLog(LoginLog loginLog);
}
