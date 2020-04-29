package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.BookMapper;
import top.maplefix.model.Book;
import top.maplefix.service.BookService;
import top.maplefix.utils.ConvertUtils;

import java.util.List;


/**
 * @author Maple
 * @description 图书service实现
 * @date 2020/3/20 11:11
 */
@Service
public class BookServiceImpl implements BookService {
    
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book selectBookById(String id) {
        return bookMapper.selectBookById(id);
    }

    @Override
    public int insertBook(Book book) {
        return bookMapper.insertBook(book);
    }

    /**
     * 修改数据
     *
     * @param book 实例对象
     * @return 实例对象
     */
    @Override
    public int updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    @Override
    public int deleteBookByIds(String ids) {
        return bookMapper.deleteBookByIds(ConvertUtils.toStrArray(ids));
    }

    @Override
    public int likeBook(String id) {
        return 0;
    }

    @Override
    public Book getBookDetail(String id) {
        return null;
    }

    @Override
    public List<Book> selectBookList(Book book) {
        return bookMapper.selectBookList(book);
    }
}
