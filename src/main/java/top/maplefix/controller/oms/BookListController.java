package top.maplefix.controller.oms;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.common.ResultCode;
import top.maplefix.constant.BookListConstant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Book;
import top.maplefix.service.BookListService;
import top.maplefix.utils.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 书单接口控制类
 * @date : 2020/2/2 11:28
 */
@RestController
@RequestMapping("/book")
@Slf4j
public class BookListController extends BaseController {

    @Autowired
    private BookListService bookListService;

    /**
     * 后台管理书单首页
     * @param request 请求
     * @return 页面
     */
    @GetMapping({"","/"})
    public String linkPage(HttpServletRequest request) {
        request.setAttribute("path", "bookList");
        return "admin/bookList";
    }

    /**
     * 书单查询
     * @param params 参数
     * @return 分页数据
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("书单分页查询开始...");
        BaseResult baseResult = new BaseResult();
        List<Book> bookListPage = bookListService.getBookListPage(params);
        PageInfo<Book> pageInfo = new PageInfo<>(bookListPage);
        log.info("书单分页查询成功...");
        return baseResult;
    }

    /**
     * 书单添加
     * @param bookList 书单实体
     * @return BaseResult
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = BusinessType.INSERT)
    @ResponseBody
    public BaseResult save(Book bookList) {
        log.info("书单新增操作开始...");
        try {
            Book bookList1 = bookListService.isExistBookList(bookList);
            if (bookList1 != null) {
                log.info("书单新增操作失败,该关键字:{}已存在",bookList.getBookName());
                return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR, "书名已存在");
            }
            bookListService.saveBookList(bookList);
            log.info("书单新增操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单新增操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR,"系统错误");
        }
    }

    /**
     * 书单修改
     * @param bookList 书单实体
     * @return BaseResult
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = BusinessType.UPDATE)
    @ResponseBody
    public BaseResult update(Book bookList) {
        log.info("书单编辑操作开始...");
        try {
            bookListService.updateBookList(bookList);
            log.info("书单编辑操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单编辑操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR,"系统错误");
        }
    }

    /**
     * 书单删除
     * @param ids 书单id
     * @return  BaseResult
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = BusinessType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("书单删除操作开始...");
        try {
            bookListService.deleteBatch(ids);
            log.info("书单删除操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR,"系统错误");
        }
    }
    /**
     * 开始阅读
     * @param bookListId 书单id
     * @return  BaseResult
     */
    @RequestMapping(value = "/startRead", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = BusinessType.UPDATE)
    @ResponseBody
    public BaseResult startRead(@RequestParam String bookListId) {
        log.info("书单开始阅读操作开始...");
        try {
            //校验该书阅读状态
            Book bookList = bookListService.selectById(bookListId);
            if(BookListConstant.READING==(bookList.getReadStatus())){
                log.error("该书{}已经在阅读中",bookList.getBookName());
                return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR, "该书已经在阅读中");
            }else if(BookListConstant.END_READ ==(bookList.getReadStatus())){
                log.error("该书{}已经阅读完",bookList.getBookName());
                return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR, "该书已经阅读完");
            }
            bookListService.startRead(bookListId);
            log.info("书单开始阅读操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单开始阅读操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR,"系统错误");
        }
    }
    /**
     * 结束阅读
     * @param bookListId 书单id
     * @return  BaseResult
     */
    @RequestMapping(value = "/endRead", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = BusinessType.UPDATE)
    @ResponseBody
    public BaseResult endRead(@RequestParam String bookListId) {
        log.info("书单结束阅读操作开始...");
        try {
            //校验该书阅读状态
            Book bookList = bookListService.selectById(bookListId);
            if(BookListConstant.NOT_READ==(bookList.getReadStatus())){
                log.error("该书{}还未开始阅读",bookList.getBookName());
                return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR, "该书还未开始阅读");
            }else if(BookListConstant.END_READ==(bookList.getReadStatus())){
                log.error("该书{}已经阅读完",bookList.getBookName());
                return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR, "该书已经阅读完");
            }
            bookListService.endRead(bookListId);
            log.info("书单结束阅读操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单结束阅读操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR,"系统错误");
        }
    }
    /**
     * 写书评
     * @param bookListId 书单id
     * @param reviews 书评内容
     * @return  BaseResult
     */
    @RequestMapping(value = "/writeReviews", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = BusinessType.UPDATE)
    @ResponseBody
    public BaseResult writeReviews(@RequestParam String bookListId,@RequestParam String reviews) {
        log.info("书单写书评操作开始...");
        try {
            bookListService.writeReviews(bookListId,reviews);
            log.info("书单写书评操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单写书评操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR,"系统错误");
        }
    }

    /**
     * 导出书单列表
     * @param ids 书单ids
     * @return excel文件名
     */
    @RequestMapping("/export")
    @OLog(module = "书单管理", businessType = BusinessType.EXPORT)
    @ResponseBody
    public BaseResult export(String[] ids, HttpServletResponse response) {
        log.info("书单导出操作开始...");
        try {
            List<Book> bookList = bookListService.selectBookListByIds(ids);
            ExcelUtil<Book> util = new ExcelUtil<>(Book.class);
            BaseResult baseResult = util.exportExcel(bookList, "bookList", response);
            log.info("书单导出操作成功...");
            return baseResult;
        }catch (Exception e){
            log.error("书单导出操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR,"系统错误");
        }
    }
}
