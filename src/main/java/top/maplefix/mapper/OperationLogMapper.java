package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import top.maplefix.model.OperationLog;

/**
 * @author : Maple
 * @description : 操作日志mapper
 * @date : Created in 2019/7/25 0:33
 * @version : v1.0
 */
@CacheNamespace
public interface OperationLogMapper extends Mapper<OperationLog>, SelectByIdsMapper<OperationLog> {

}
