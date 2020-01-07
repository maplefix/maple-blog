package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import top.maplefix.model.Category;

/**
 * @author : Maple
 * @description : 博客分类mapper
 * @date : Created in 2019/7/25 0:30
 * @version : v1.0
 */
@CacheNamespace
public interface CategoryMapper extends Mapper<Category>, SelectByIdsMapper<Category> {

}
