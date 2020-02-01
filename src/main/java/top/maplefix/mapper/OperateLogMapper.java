package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import top.maplefix.model.OperateLog;

/**
 * @author : Maple
 * @description : 操作日志mapper
 * @date : 2019/7/25 0:33
 * @version : v1.0
 */
@CacheNamespace
public interface OperateLogMapper extends Mapper<OperateLog>, SelectByIdsMapper<OperateLog> {

}
