package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Dict;

/**
 * @author : Maple
 * @description : 字典mapper
 * @date : Created in 2019/7/25 0:31
 * @editor:
 * @version: v2.1
 */
@CacheNamespace
public interface DictMapper extends Mapper<Dict> {

}
