package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.BookListConstant;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.BookListMapper;
import top.maplefix.model.BookList;
import top.maplefix.service.IBookListService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.UuidUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description: 书单接口service实现类
 * @date : 2019/12/2 11:02
 * @version v1.0
 */
@Service
public class BookListServiceImpl implements IBookListService {

    @Autowired
    private BookListMapper bookListMapper;

    @Override
    public List<BookList> getBookListPage(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        String bookName = top.maplefix.utils.StringUtils.getObjStr(params.get("bookName"));
        String bookAuthor = top.maplefix.utils.StringUtils.getObjStr(params.get("bookAuthor"));
        Example example = new Example(BookList.class);
        //example.orderBy("readStatus").desc().orderBy("createDate").desc();
        example.setOrderByClause ("readStatus desc , createDate desc");
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(bookName)){
            criteria.andEqualTo("bookName", bookName);
        }
        if(!StringUtils.isEmpty(bookAuthor)){
            criteria.andEqualTo("bookAuthor", bookAuthor);
        }
        //首页查询的书单数据不要分页，直接查出所有即可
        if(currPage != 0 && pageSize != 0){
            PageHelper.startPage(currPage,pageSize);
        }
        return bookListMapper.selectByExample(example);
    }

    @Override
    public BookList isExistBookList(BookList bookList) {
        Example example = new Example(BookList.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bookName",bookList.getBookName());
        return bookListMapper.selectOneByExample(example);
    }

    @Override
    public BookList selectById(String bookListId) {
        BookList bookList = new BookList();
        bookList.setBookListId(bookListId);
        return bookListMapper.selectByPrimaryKey(bookList);
    }

    @Override
    public void saveBookList(BookList bookList) {
        bookList.setReadStatus(BookListConstant.NOT_READ);
        bookList.setBookListId(UuidUtils.getRandomUuidWithoutSeparator());
        bookList.setCreateDate(DateUtils.getCurrDate());
        bookListMapper.insert(bookList);
    }

    @Override
    public void deleteBatch(String[] bookListIds) {
        for (String bookListId : bookListIds){
            BookList bookList = new BookList();
            bookList.setBookListId(bookListId);
            bookListMapper.delete(bookList);
        }
    }

    @Override
    public void updateBookList(BookList bookList) {
        bookList.setUpdateDate(DateUtils.getCurrDate());
        bookListMapper.updateByPrimaryKeySelective(bookList);
    }

    @Override
    public void startRead(String bookListId) {
        BookList bookList = new BookList();
        bookList.setReadStatus(BookListConstant.READING);
        bookList.setBookListId(bookListId);
        bookList.setReadBeginDate(DateUtils.getCurrDate());
        bookList.setUpdateDate(DateUtils.getCurrDate());
        bookListMapper.updateByPrimaryKeySelective(bookList);
    }

    @Override
    public void endRead(String bookListId) {
        BookList bookList = new BookList();
        bookList.setReadStatus(BookListConstant.END_READ);
        bookList.setBookListId(bookListId);
        bookList.setReadEndDate(DateUtils.getCurrDate());
        bookList.setUpdateDate(DateUtils.getCurrDate());
        bookListMapper.updateByPrimaryKeySelective(bookList);
    }

    @Override
    public void writeReviews(String bookListId,String reviews) {
        BookList bookList = new BookList();
        bookList.setBookListId(bookListId);
        bookList.setReviews(reviews);
        bookList.setUpdateDate(DateUtils.getCurrDate());
        bookListMapper.updateByPrimaryKeySelective(bookList);
    }

    @Override
    public List<BookList> selectBookListByIds(String[] ids) {
        //如果前端没选中列表数据则全部导出
        if(null == ids || ids.length == 0){
            Example example = new Example(BookList.class);
            example.setOrderByClause ("readStatus desc , createDate desc");
            return bookListMapper.selectByExample(example);
        }
        //将数组转成字符串，用逗号隔开
        String idsStr = StringUtils.join(ids,",");
        return bookListMapper.selectByIdList(Arrays.asList(ids));
    }
}
