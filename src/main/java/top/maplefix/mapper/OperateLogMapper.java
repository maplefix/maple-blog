package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.OperateLog;

import java.util.List;

/**
 * @author : Maple
 * @description : 操作日志mapper
 * @date : 2020/1/25 0:33 
 */
public interface OperateLogMapper extends Mapper<OperateLog>{

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
     * @param ids      需要删除的数据
     * @return 结果
     */
    int deleteOperateLogByIds(@Param("ids") String[] ids);

    /**
     * 查询操作日志详细
     *
     * @param id 操作ID
     * @return 操作日志对象
     */
    OperateLog selectOperateLogById(@Param("id")String id);

    /**
     * 清空操作日志
     */
    void cleanOperateLog();

    /**
     * update operate log
     *
     * @param operateLog operateLog
     * @return update count
     */
    int updateOperateLog(OperateLog operateLog);
}
