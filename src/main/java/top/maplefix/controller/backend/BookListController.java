package top.maplefix.controller.backend;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.BookListConstant;
import top.maplefix.constant.Constant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.OperationType;
import top.maplefix.enums.ResultCode;
import top.maplefix.model.BookList;
import top.maplefix.service.IBookListService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: wangjg on 2019/12/2 11:28
 * @description: 书单接口控制类
 * @editored:
 * @version v 2.0
 */
@Controller
@RequestMapping("/api/admin/bookList")
@Slf4j
public class BookListController extends BaseController {

    @Autowired
    private IBookListService bookListService;

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
        if (!checkPageParam(params)) {
            baseResult.setCode(ResultCode.LACK_PARAM_CODE.getCode());
            baseResult.setMsg(Constant.SUCCESS_MSG);
            log.info("书单分页查询失败,缺少分页参数...");
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        List<BookList> bookListPage = bookListService.getBookListPage(params);
        PageInfo<BookList> pageInfo = new PageInfo<>(bookListPage);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("书单分页查询成功...");
        return baseResult;
    }

    /**
     * 书单添加
     * @param bookList 书单实体
     * @return BaseResult
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = OperationType.INSERT)
    @ResponseBody
    public BaseResult save(BookList bookList) {
        log.info("书单新增操作开始...");
        try {
            BookList bookList1 = bookListService.isExistBookList(bookList);
            if (bookList1 != null) {
                log.info("书单新增操作失败,该关键字:{}已存在",bookList.getBookName());
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "书名已存在");
            }
            bookListService.saveBookList(bookList);
            log.info("书单新增操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单新增操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 书单修改
     * @param bookList 书单实体
     * @return BaseResult
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult update(BookList bookList) {
        log.info("书单编辑操作开始...");
        try {
            bookListService.updateBookList(bookList);
            log.info("书单编辑操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单编辑操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 书单删除
     * @param ids 书单id
     * @return  BaseResult
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("书单删除操作开始...");
        try {
            bookListService.deleteBatch(ids);
            log.info("书单删除操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
    /**
     * 开始阅读
     * @param bookListId 书单id
     * @return  BaseResult
     */
    @RequestMapping(value = "/startRead", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult startRead(@RequestParam String bookListId) {
        log.info("书单开始阅读操作开始...");
        try {
            //校验该书阅读状态
            BookList bookList = bookListService.selectById(bookListId);
            if(BookListConstant.READING.equals(bookList.getReadStatus())){
                log.error("该书{}已经在阅读中",bookList.getBookName());
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "该书已经在阅读中");
            }else if(BookListConstant.END_READ.equals(bookList.getReadStatus())){
                log.error("该书{}已经阅读完",bookList.getBookName());
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "该书已经阅读完");
            }
            bookListService.startRead(bookListId);
            log.info("书单开始阅读操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单开始阅读操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
    /**
     * 结束阅读
     * @param bookListId 书单id
     * @return  BaseResult
     */
    @RequestMapping(value = "/endRead", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult endRead(@RequestParam String bookListId) {
        log.info("书单结束阅读操作开始...");
        try {
            //校验该书阅读状态
            BookList bookList = bookListService.selectById(bookListId);
            if(BookListConstant.NOT_READ.equals(bookList.getReadStatus())){
                log.error("该书{}还未开始阅读",bookList.getBookName());
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "该书还未开始阅读");
            }else if(BookListConstant.END_READ.equals(bookList.getReadStatus())){
                log.error("该书{}已经阅读完",bookList.getBookName());
                return BaseResult.failResult(ResultCode.FAIL_CODE.getCode(), "该书已经阅读完");
            }
            bookListService.endRead(bookListId);
            log.info("书单结束阅读操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单结束阅读操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
    /**
     * 写书评
     * @param bookListId 书单id
     * @param reviews 书评内容
     * @return  BaseResult
     */
    @RequestMapping(value = "/writeReviews", method = RequestMethod.POST)
    @OLog(module = "书单管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult writeReviews(@RequestParam String bookListId,@RequestParam String reviews) {
        log.info("书单写书评操作开始...");
        try {
            bookListService.writeReviews(bookListId,reviews);
            log.info("书单写书评操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("书单写书评操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
}
