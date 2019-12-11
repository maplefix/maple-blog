package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Category;

/**
 * @author : Maple
 * @description : 博客分类mapper
 * @date : Created in 2019/7/25 0:30
 * @editor:
 * @version: v2.1
 */
@CacheNamespace
public interface CategoryMapper extends Mapper<Category> {

}
