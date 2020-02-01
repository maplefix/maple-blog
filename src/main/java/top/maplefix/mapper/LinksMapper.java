package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import top.maplefix.model.Link;

/**
 * @author : Maple
 * @description : 友链mapper
 * @date : 2019/7/25 0:32
 * @version : v1.0
 */
@CacheNamespace
public interface LinksMapper extends Mapper<Link> , SelectByIdsMapper<Link> {

}
