package top.maplefix.controller.backend;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.constant.FileItemConstant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.OperationType;
import top.maplefix.enums.ResultCode;
import top.maplefix.model.Blog;
import top.maplefix.service.IBlogService;
import top.maplefix.service.ICategoryService;
import top.maplefix.service.IFileItemService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.ExcelUtil;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.file.FileSuffixUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Maple
 * @description : 后端博客管理controller
 * @date : Created in 2019/7/27 16:33
           Edited in 2019/10/28 16:47
 * @version : v1.0
 */
@Controller
@RequestMapping("/api/admin/blog")
@Slf4j
public class BlogController extends BaseController {

    @Resource
    private IBlogService blogService;

    @Resource
    private ICategoryService categoryService;

    @Autowired
    private IFileItemService fileItemService;

    private static final Integer length = 100;

    @Value("${file.allowType}")
    private String allowTypes;

    /**
     * 博客页面
     * @param request
     * @return
     */
    @GetMapping({"", "/"})
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "blog");
        return "admin/blog";
    }

    /**
     * 分页查询
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("博客分页查询开始...");
        BaseResult baseResult = new BaseResult();
        if (!checkPageParam(params)) {
            log.error("博客分页查询失败,缺少分页参数");
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        //后端查询查出所有状态的文章，包括删除和草稿箱的文章
        params.put("isAll","true");
        List<Blog> blogList = blogService.selectBlogForIndexPage(params);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("博客分页查询成功...");
        return baseResult;
    }

    /**
     * 博客编辑页面
     * @param request
     * @return
     */
    @GetMapping("/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        request.setAttribute("category", categoryService.getAllCategory());
        return "admin/edit";
    }

    /**
     * 指定博客编辑页面
     * @param request
     * @param blogId
     * @return
     */
    @GetMapping("/edit/{blogId}")
    public String edit(HttpServletRequest request, @PathVariable("blogId") String blogId) {
        request.setAttribute("path", "edit");
        Blog temp = new Blog();
        temp.setBlogId(blogId);
        Blog blog = blogService.getBlog(temp);
        if (blog == null) {
            return "error/error_400";
        }
        request.setAttribute("blog", blog);
        request.setAttribute("category", categoryService.getAllCategory());
        return "admin/edit";
    }

    /**
     * 更新博客
     * @param blogId 博客io
     * @param title 标题
     * @param coverImg 封面图路径
     * @param content 内容
     * @param summary 摘要
     * @param categoryId 分类id
     * @param status 状态（发布/草稿）
     * @param height 权重
     * @return
     */
    @PostMapping("/update")
    @OLog(module = "博客管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public BaseResult update(@RequestParam("blogId") String blogId,
                         @RequestParam("title") String title,
                         @RequestParam("coverImg") String coverImg,
                         @RequestParam("content") String content,
                         @RequestParam("label") String label,
                         @RequestParam(value = "summary",required = false) String summary,
                         @RequestParam("categoryId") String categoryId,
                         @RequestParam("status") String  status,
                         @RequestParam(name = "height", required = false) Integer height) {
        log.info("博客编辑操作开始...");
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setTitle(title);
        //此处修改标签时应该先判断是否有修改，如有则先删除原本的博客和标签关系再重新插入数据
        blog.setLabel(blogService.parseLabel(blogId,label));
        blog.setCoverImg(coverImg);
        blog.setContent(content);
        int index = content.length();
        if(StringUtils.isEmpty(summary)){
            if(index > length){
                summary = content.substring(0,length);
            }else {
                summary = content;
            }
        }
        blog.setSummary(summary);
        blog.setCategoryId(categoryId);
        blog.setStatus(status);
        blog.setHeight(height);
        blog.setDelFlag(Constant.NORMAL);
        try {
            blog.setUpdateDate(DateUtils.getCurrDate());
            blogService.updateBlog(blog);
            log.info("博客编辑操作成功...");
            return new BaseResult();
        }catch (Exception e){
            log.error("博客编辑操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 新增博客
     * @param blog
     * @return
     */
    @PostMapping("/save")
    @OLog(module = "博客管理", businessType = OperationType.INSERT)
    @ResponseBody
    public BaseResult save(Blog blog) {
        log.info("博客新增操作开始...");
        try {
            blog.setCreateDate(DateUtils.getCurrDate());
            int index = blog.getContent().length();
            if(StringUtils.isEmpty(blog.getSummary())){
                if(index > length){
                    blog.setSummary(blog.getContent().substring(0,length));
                }else {
                    blog.setSummary(blog.getContent());
                }
            }
            boolean result = blogService.insert(blog);
            if (result) {
                log.info("博客新增操作成功...");
                return new BaseResult("添加成功");
            } else {
                log.error("博客新增操作失败");
                return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
            }
        }catch (Exception e){
            log.error("博客新增操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 删除博客
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @OLog(module = "博客管理", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody String[] ids) {
        log.info("博客删除操作开始...");
        BaseResult baseResult = new BaseResult();
        if (ids.length < 1) {
            log.error("博客删除操作失败，缺少必须参数");
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        try {
            blogService.deleteBatch(ids);
            log.info("博客删除操作成功...");
            return baseResult;
        }catch (Exception e){
            log.error("博客删除操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }

    /**
     * 该接口作为editor.md上传文件和封面上传文件共用接口
     * 返回格式约定如下：
     * {
     *   code : 0 | 1,           // 0 表示上传失败，1 表示上传成功
     *   message : "提示的信息，上传成功或上传失败及错误信息等。",
     *   url     : "图片地址"        // 上传成功时才返回
     * }
     * @param request
     * @param serverType 文件存储类型（1表示在本地存储，2表示存储在七牛云）
     */
    @PostMapping(value = "/upload",produces= MediaType.APPLICATION_JSON_VALUE +";charset=utf-8")
    @OLog(module = "博客管理", businessType = OperationType.UPLOAD)
    @ResponseBody
    public JSONObject uploadEditorFile(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "editormd-image-file", required = false) MultipartFile file,
                                       @RequestParam(value = "serverType", required = false) String serverType){
        log.info("editor.md上传文件操作开始...");
        JSONObject result = new JSONObject();
        if(StringUtils.isEmpty(serverType)){
            serverType = FileItemConstant.QINIU_STORE;
        }
        try {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String[] allowType = allowTypes.split("|");
            boolean valid = FileSuffixUtils.isValid(suffixName, allowType);
            if(!valid){
                log.error("上传文件失败，原因为 ：文件格式["+suffixName+"]不支持");
                result.put("success","0");
                result.put("message","上传文件失败，原因为 ：文件格式["+suffixName+"]不支持");
                return result;
            }
            Objects.requireNonNull(serverType, "上传服务器未选定，请重试！");
            String path = null;
            if (FileItemConstant.QINIU_STORE == serverType) {
                path = fileItemService.insertQiNiuYunImageFile(file);
            } else if (FileItemConstant.LOCAL_STORE == serverType) {
                path = fileItemService.insertLocalImageFile(file);
            }
            result.put("success",1);
            result.put("message","上传成功");
            result.put("url", path);
            log.info("editor.md上传文件操作成功...");
        } catch (Exception e) {
            log.error("上传文件失败，原因为 ：" + e);
            result.put("success",0);
            result.put("message","上传文件失败，原因为 ：" + e.getMessage());
            log.info("editor.md上传文件操作异常，异常信息:{},异常堆栈:{}",e.getMessage(),e);
        }
        System.out.println(result.toString());
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        return result;
    }

    /**
     * 导出博客列表
     * @param ids 博客ids
     * @return excel文件名
     */
    @PostMapping("/export")
    @OLog(module = "博客管理", businessType = OperationType.EXPORT)
    @ResponseBody
    public BaseResult export(String[] ids, HttpServletResponse response) {
        log.info("博客导出操作开始...");
        try {
            List<Blog> blogList = blogService.selectBlogByIds(ids);
            ExcelUtil<Blog> util = new ExcelUtil<>(Blog.class);
            BaseResult baseResult = util.exportExcel(blogList, "blogList", response);
            log.info("博客导出操作成功...");
            return baseResult;
        }catch (Exception e){
            log.error("博客导出操作异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            return BaseResult.failResult(ResultCode.SYSTEM_ERROR_CODE.getCode());
        }
    }
}
