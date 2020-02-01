package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.LoginLog;
import top.maplefix.model.OperateLog;
import top.maplefix.model.VisitLog;

import java.util.List;

/**
 * @author : Maple
 * @description : 访问日志mapper
 * @date : 2019/7/25 0:34
 * @version : v1.0
 */
@CacheNamespace
public interface DashboardMapper extends Mapper<LoginLog> {

    /**
     * 查询五条最新的登录信息
     * @return
     */
    List<LoginLog> getLoginLogData();
    /**
     * 查询五条最新的操作信息
     * @return
     */
    List<OperateLog> getOperationLogData();
    /**
     * 查询五条最新的访问信息
     * @return
     */
    List<VisitLog> getVisitLogData();
}
