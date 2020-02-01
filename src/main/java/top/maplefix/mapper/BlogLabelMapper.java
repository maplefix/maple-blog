package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.BlogLabel;

/**
 * @author : Maple
 * @description : 博客标签关联mapper
 * @date : 2019/7/25 17:17
 * @version : v1.0
 */
@CacheNamespace
public interface BlogLabelMapper extends Mapper<BlogLabel> {

}
