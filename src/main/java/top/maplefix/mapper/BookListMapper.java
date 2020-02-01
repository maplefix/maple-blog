package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import top.maplefix.model.Book;

/**
 * @author : Maple
 * @description: 书单mapper
 * @date : 2019/12/2 10:57
 * @version : v1.0
 */
@CacheNamespace
public interface BookListMapper extends Mapper<Book>, SelectByIdsMapper<Book>, SelectByIdListMapper<Book, String> {

}
