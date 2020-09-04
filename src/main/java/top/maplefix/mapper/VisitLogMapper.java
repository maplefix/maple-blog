package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.VisitLog;

import java.util.List;

/**
 * @author : Maple
 * @description : 访问日志mapper
 * @date : 2020/2/25 0:34 
 */
public interface VisitLogMapper extends Mapper<VisitLog> {

    /**
     * 新增系统访问日志
     *
     * @param visitLog 访问日志对象
     */
    int insertVisitLog(VisitLog visitLog);

    /**
     * 查询系统访问日志集合
     *
     * @param visitLog 访问日志对象
     * @return 记录集合
     */
    List<VisitLog> selectVisitLogList(VisitLog visitLog);

    /**
     * 批量删除访问日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteVisitLogByIds(@Param("ids") Long[] ids);

    /**
     * f
     * 清空访问日志
     *
     * @param username 操作者
     * @return 结果
     */
    int cleanVisitLog(String username);

    /**
     * update visitLog
     *
     * @param visitLog VisitLog
     * @return update count
     */
    int updateVisitLog(VisitLog visitLog);
}
