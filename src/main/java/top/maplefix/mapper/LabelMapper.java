package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import top.maplefix.model.Label;

/**
 * @author : Maple
 * @description : 博客标签mapper
 * @date : 2019/7/25 0:31
 * @version : v1.0
 */
@CacheNamespace
public interface LabelMapper extends Mapper<Label>, SelectByIdsMapper<Label> {

}
