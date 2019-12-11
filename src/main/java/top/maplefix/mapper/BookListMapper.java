package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.BookList;

/**
 * @author: wangjg on 2019/12/2 10:57
 * @description: 书单mapper
 * @editored:
 * @version v 2.0
 */
@CacheNamespace
public interface BookListMapper extends Mapper<BookList> {

}
