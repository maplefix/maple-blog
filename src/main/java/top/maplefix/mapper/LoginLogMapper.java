package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import top.maplefix.model.LoginLog;

/**
 * @author : Maple
 * @description : 登录日志mapper
 * @date : Created in 2019/7/25 0:32
 * @version : v1.0
 */
@CacheNamespace
public interface LoginLogMapper extends Mapper<LoginLog> , SelectByIdsMapper<LoginLog> {

}
