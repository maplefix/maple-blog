package top.maplefix.controller.front;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.maplefix.annotation.VLog;
import top.maplefix.constant.BlogConstant;
import top.maplefix.constant.BookListConstant;
import top.maplefix.constant.LinksConstant;
import top.maplefix.constant.PageConstant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BookListStatus;
import top.maplefix.model.*;
import top.maplefix.service.*;
import top.maplefix.utils.PageData;
import top.maplefix.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Maple
 * @description : 博客前端控制类
 * @date : Created in 2019/7/24 22:26
 Edited in 2019/10/28 14:20* @version : v2.1
 */
@Controller
@Slf4j
public class BlogFrontController extends BaseController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private ILinksService linksService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private IBookListService bookListService;
    /**
     * 前端主题
     */
    @Value("${maple.theme}")
    private String theme;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping({"/", "/index", "index.html"})
    @VLog(module = "首页")
    public String index(HttpServletRequest request) {
        return this.page(request,1,10);
    }

    /**
     * 设置前台页面公用的部分代码
     * 均设置Redis缓存
     */
    private void setCommonMessage(HttpServletRequest request) {

        //查询点击最多博客列表
        request.setAttribute("hotBlog", blogService.selectBlogForNewestOrHottest(BlogConstant.BLOG_HOTTEST));
        //查询最热博客标签
        request.setAttribute("hotLabel", labelService.getBlogTagForIndex());
        //查询首页最热分类
        request.setAttribute("hotCategories", blogService.selectBlogForHotCategory());
    }

    /**
     * 设置字典信息
     * @param request
     */
    private void setDictMessage(HttpServletRequest request){
        //查出字典表中的系统展示字段配置,//KeyWord.SYSTEM_CONFIG.getValue()
        List<Dict> config = dictService.getSystemConfig("");
        Map<String, Object> dictMap = config.stream().collect(Collectors.toMap(Dict::getKeyWord,Dict::getDictValue));
        request.setAttribute("dict", dictMap);
    }

    /**
     * 设置分页参数
     * @param page
     * @return
     */
    private Map<String, Object> setPageParams(Integer page){
        Map<String, Object> params = new HashMap<>(16);
        params.put(PageConstant.PAGENUM, page);
        params.put(PageConstant.PAGESIZE, 10);
        return params;
    }
    /**
     * 首页数据加载
     * @param request
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping({"/page/{pageNum}.html"})
    @VLog(module = "首页")
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        log.info("首页查询页面访问开始...");
        Map<String, Object> params = new HashMap<>(16);
        params.put(PageConstant.PAGENUM,pageNum);
        params.put(PageConstant.PAGESIZE,pageSize);
        //这里将isAll置为null或者不复制，表明是前端查询，只查出delFlag为0且blogStatus为1的文章
        params.put("isAll",null);
        List<Blog> blogList = blogService.selectBlogForIndexPage(params);
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogList);
        PageData page = new PageData(pageInfo);
        if (blogList == null || blogList.size() <1) {
            log.info("博客查询页面访问失败...");
            return "error/error_404";
        }
        setCommonMessage(request);
        setDictMessage(request);
        request.setAttribute("pageName", "首页");
        //将首页博客列表放到request域对象中
        request.setAttribute("blogPageResult", page);
        log.info("首页查询页面访问成功...");
        return "blog/" + theme + "/index";
    }

    /**
     * Category页面(包括分类数据和标签数据)
     *
     * @return
     */
    @GetMapping({"/category.html"})
    @VLog(module = "分类")
    public String category(HttpServletRequest request) {
        log.info("分类查询页面访问开始...");
        request.setAttribute("hotLabel", labelService.getBlogTagForIndex());
        request.setAttribute("category", categoryService.getAllCategory());
        request.setAttribute("pageName", "分类页面");
        setDictMessage(request);
        log.info("分类查询页面访问成功...");
        return "blog/" + theme + "/category";
    }
    /**
     * 归档页面
     *
     * @return
     */
    @GetMapping({"/archive.html"})
    @VLog(module = "归档")
    public String archive(HttpServletRequest request) {
        log.info("归档查询页面访问开始...");
        setCommonMessage(request);
        setDictMessage(request);
        request.setAttribute("archive", blogService.selectArchives());
        request.setAttribute("pageName", "归档页面");
        log.info("归档查询页面访问成功...");
        return "blog/" + theme + "/archive";
    }

    /**
     *  博客详情页
     * @param request
     * @param blogId 博客id
     * @return
     */
    @GetMapping({"/blog/{blogId}.html", "/article/{blogId}.html"})
    @VLog(module = "博客")
    public String detail(HttpServletRequest request, @PathVariable("blogId") String blogId) {
        log.info("博客详情查询页面访问开始...");
        Blog tmp = new Blog();
        tmp.setBlogId(blogId);
        Blog blog = blogService.getBlog(tmp);
        if (blog != null) {
            blog.setLabels(Arrays.asList(blog.getLabel().split(",")));
            addHits(blog);
            request.setAttribute("blog", blog);
            request.setAttribute("pageName", "详情");
            setDictMessage(request);
            log.info("博客详情查询页面访问成功...");
            return "blog/" + theme + "/detail";
        }
        log.info("博客详情查询页面访问成功...");
        return "error/error_404";
    }

    /**
     * 标签列表页
     * @param request
     * @param labelName 标签名
     * @return
     */
    @GetMapping({"/label/{labelName}.html"})
    @VLog(module = "标签")
    public String label(HttpServletRequest request, @PathVariable("labelName") String labelName) {
        return label(request, labelName, 1);
    }

    /**
     * 标签列表页
     *
     * @return
     */
    @GetMapping({"/label/{labelName}/{page}.html"})
    @VLog(module = "标签")
    public String label(HttpServletRequest request, @PathVariable("labelName") String labelName, @PathVariable("page") Integer page) {
        log.info("博客标签查询页面访问开始...");
        Map<String, Object> params = setPageParams(page);
        params.put("labelName", labelName);
        List<Blog> blogList = blogService.selectBlogForIndexPage(params);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        PageData pageData = new PageData(pageInfo);
        request.setAttribute("blogPageResult", pageData);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "label");
        request.setAttribute("keyword", labelName);
        setCommonMessage(request);
        setDictMessage(request);
        log.info("博客标签查询页面访问成功...");
        return "blog/" + theme + "/list";
    }

    /**
     * 分类列表页
     *
     * @return
     */
    @GetMapping({"/category/{categoryName}.html"})
    @VLog(module = "分类")
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName) {
        return category(request, categoryName, 1);
    }

    /**
     * 分页查看分类列表页
     * @param request
     * @param categoryName
     * @param page
     * @return
     */
    @GetMapping({"/category/{categoryName}/{page}.html"})
    @VLog(module = "分类")
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName,
                           @PathVariable("page") Integer page) {
        log.info("分类查询页面访问开始...");
        Map<String, Object> params = setPageParams(page);
        params.put("categoryName", categoryName);
        List<Category> categories = categoryService.getBlogCategoryPage(params);
        if(categories == null || categories.isEmpty()){
            return "error/error_404";
        }
        params.put("categoryId",categories.get(0).getCategoryId());
        List<Blog> blogList = blogService.selectBlogForIndexPage(params);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        PageData pageData = new PageData(pageInfo);
        request.setAttribute("blogPageResult", pageData);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        setCommonMessage(request);
        setDictMessage(request);
        log.info("分类查询页面访问成功...");
        return "blog/" + theme + "/list";
    }

    /**
     * 搜索列表页
     * @param request
     * @param keyword 关键字
     * @return
     */
    @GetMapping({"/search/{keyword}.html"})
    @VLog(module = "博客")
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        return search(request, keyword, 1);
    }

    /**
     * 搜索列表页
     *
     * @return
     */
    @GetMapping({"/search/{keyword}/{page}.html"})
    @VLog(module = "博客")
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword, @PathVariable("page") Integer page) {
        log.info("博客查询页面访问开始...");
        Map<String, Object> params = setPageParams(page);
        params.put("keyword", keyword);
        List<Blog> blogList = blogService.selectBlogForIndexPage(params);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        PageData pageData = new PageData(pageInfo);
        request.setAttribute("blogPageResult", pageData);
        request.setAttribute("pageName", "搜索");
        request.setAttribute("pageUrl", "search");
        request.setAttribute("keyword", keyword);
        setCommonMessage(request);
        setDictMessage(request);
        log.info("博客查询页面访问成功...");
        return "blog/" + theme + "/list";
    }


    /**
     * 友情链接页
     *
     * @return
     */
    @GetMapping({"/link.html"})
    @VLog(module = "友链")
    public String link(HttpServletRequest request) {
        log.info("友链页面访问开始...");
        request.setAttribute("pageName", "友情链接");
        Map<String, Object> params = new HashMap<>(16);
        params.put(PageConstant.PAGENUM, 1);
        params.put(PageConstant.PAGESIZE, 100);
        params.put("linksType",LinksConstant.LINK_FRIEND);
        List<Links> linkList = linksService.getLinksPage(params);
        request.setAttribute("favoriteLinks", linkList);
        params.remove("linksType");
        params.put("linksType",LinksConstant.LINK_RECOMMEND);
        List<Links> recommendList = linksService.getLinksPage(params);
        request.setAttribute("recommendLinks", recommendList);
        setDictMessage(request);
        request.setAttribute("pageName", "友链");
        log.info("友链页面访问成功...");
        return "blog/" + theme + "/link";
    }


    /**
     * 关于页面
     *
     * @return
     */
    @GetMapping({"/about.html"})
    @VLog(module = "关于")
    public String about(HttpServletRequest request) {
        log.info("关于我页面访问开始...");
        setDictMessage(request);
        request.setAttribute("pageName", "关于我");
        log.info("关于我页面访问成功...");
        return "blog/" + theme + "/about";
    }

    /**
     * reading页面
     * @param request 请求
     * @return reading页面
     */
    @GetMapping({"/reading.html"})
    @VLog(module = "书单")
    public String reading(HttpServletRequest request){
        log.info("reading页面访问开始...");
        setDictMessage(request);
        Map<String,Object> params = new HashMap<>();
        List<BookList> bookListPage = bookListService.getBookListPage(params);
        for(BookList bookList : bookListPage){
            //阅读状态格式化
            if(BookListConstant.NOT_READ.equals(bookList.getReadStatus())){
                bookList.setReadStatus(BookListStatus.NOT_READ.getValue());
            }else if(BookListConstant.READING.equals(bookList.getReadStatus())){
                bookList.setReadStatus(BookListStatus.READING.getValue());
            }else if(BookListConstant.END_READ.equals(bookList.getReadStatus())){
                bookList.setReadStatus(BookListStatus.END_READ.getValue());
            }
            //时间格式化
            if(!StringUtils.isEmpty(bookList.getReadBeginDate())){
              bookList.setReadBeginDate(bookList.getReadBeginDate().substring(0,10));
            }
            if(!StringUtils.isEmpty(bookList.getReadEndDate())){
                bookList.setReadEndDate(bookList.getReadEndDate().substring(0,10));
            }
            log.info("书单列表查询成功...");
        }
        request.setAttribute("bookListPage",bookListPage);
        request.setAttribute("pageName","Reading");
        log.info("reading页面访问成功...");
        return "blog/" + theme + "/reading";
    }

    /**
     * 文章增加阅览数
     * @param blog 博客实体
     */
    public void addHits(Blog blog){
        Blog tmp = new Blog();
        tmp.setBlogId(blog.getBlogId());
        if(blog.getHits() == null){
            blog.setHits(0);
        }
        tmp.setHits(blog.getHits() +1 );
        blogService.updateBlog(tmp);
    }
}
