package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.User;

/**
 * @author : Maple
 * @description : 用户mapper
 * @date : Created in 2019/7/25 0:33
 * @version : v1.0
 */
@CacheNamespace
public interface UserMapper extends Mapper<User> {

}
