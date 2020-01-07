package top.maplefix.service;

import top.maplefix.model.BookList;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description: 书单接口service
 * @date : on 2019/12/2 10:59
 * @version v1.0
 */
public interface IBookListService {

    /**
     * 分页查询书单数据
     * @param params
     * @return
     */
    List<BookList> getBookListPage(Map<String, Object> params);

    /**
     * 查询书单是否已存在
     * @param bookList
     * @return
     */
    BookList isExistBookList(BookList bookList);

    /**
     * 根据id查询书单信息
     * @param bookListId
     * @return
     */
    BookList selectById(String bookListId);

    /**
     * 保存书单
     * @param bookList
     */
    void saveBookList(BookList bookList);

    /**
     * 根据id批量删除
     * @param bookListIds
     */
    void deleteBatch(String[] bookListIds );

    /**
     * 编辑书单
     * @param bookList
     */
    void updateBookList(BookList bookList);

    /**
     * 开始阅读
     * @param bookListId 书单id
     * @return BookList
     */
    void startRead(String bookListId);

    /**
     * 结束阅读
     * @param bookListId BaseResult
     * @return BookList
     */
    void endRead(String bookListId);

    /**
     * 写书评
     * @param bookListIds 书单id
     * @param reviews 书评内容
     */
    void writeReviews(String bookListIds,String reviews);

    /**
     * 根据id查询书列表
     * @param ids 书单ids
     * @return
     */
    List<BookList> selectBookListByIds(String[] ids);
}
