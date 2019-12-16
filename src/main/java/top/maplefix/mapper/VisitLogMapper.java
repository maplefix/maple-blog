package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import top.maplefix.model.VisitLog;

/**
 * @author : Maple
 * @description : 访问日志mapper
 * @date : Created in 2019/7/25 0:34
 * @editor:
 * @version: v2.1
 */
@CacheNamespace
public interface VisitLogMapper extends Mapper<VisitLog> , SelectByIdsMapper<VisitLog> {

}
