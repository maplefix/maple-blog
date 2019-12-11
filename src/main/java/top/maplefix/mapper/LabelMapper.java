package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Label;

/**
 * @author : Maple
 * @description : 博客标签mapper
 * @date : Created in 2019/7/25 0:31
 * @editor:
 * @version: v2.1
 */
@CacheNamespace
public interface LabelMapper extends Mapper<Label> {

}
