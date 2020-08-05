package top.maplefix.controller.fe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.VLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.model.Blog;
import top.maplefix.model.Comment;
import top.maplefix.model.Link;
import top.maplefix.service.FrontService;
import top.maplefix.utils.HttpUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.vo.BlogQuery;
import top.maplefix.vo.FrontMenu;
import top.maplefix.vo.page.TableDataInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客前端数据接口
 * @date : 2020/3/12 22:26
 */
@RestController
@RequestMapping("/f")
@Slf4j
public class FrontController extends BaseController {

    @Autowired
    private FrontService frontService;

    private static final String QQ_QUERY_URL = "https://r.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg";


    @GetMapping("/link")
    @VLog(module = "查看友链")
    public BaseResult link() {
        return BaseResult.success(frontService.selectLinkList());
    }

    @GetMapping("/link/support")
    public BaseResult supportLink() {
        return BaseResult.success(frontService.selectSupportLinkList());
    }

    @PutMapping("/link/{id}")
    @VLog(module = "友链跳转")
    public BaseResult linkRedirect(@PathVariable Long  id) {
        return BaseResult.success(frontService.incrementLinkClick(id));
    }

    /**
     * 申请link
     */
    @PostMapping("/link")
    @VLog(module = "申请友链")
    public BaseResult insertLink(@RequestBody Link link) {
        return toResult(frontService.insertLink(link));
    }

    /**
     * 网站菜单
     */
    @GetMapping("/menus")
    public BaseResult menu() {
        List<FrontMenu> menuList = new ArrayList<>();
        menuList.add(new FrontMenu("时光轴", 1, false, "/archive"));
        menuList.add(new FrontMenu("友链", 2, false, "/link"));
        menuList.add(new FrontMenu("留言", 3, false, "/leaveComment"));
        menuList.add(new FrontMenu("关于", 4, false, "/about"));
        return BaseResult.success(menuList);
    }

    /**
     * 查询分类
     * @return
     */
    @GetMapping("/categories")
    public BaseResult categories() {
        return BaseResult.success(frontService.selectCategoryList());
    }

    /**
     * 查询推荐博客
     * @return
     */
    @GetMapping("/support")
    public BaseResult support() {
        return BaseResult.success(frontService.selectSupportBlogList());
    }

    /**
     * 查询最热博客（5条）
     * @return
     */
    @GetMapping("/hot")
    public BaseResult hot() {
        return BaseResult.success(frontService.selectHotBlogList());
    }

    /**
     * 查询标签列表
     * @return
     */
    @GetMapping("/tag")
    public BaseResult tag() {
        return BaseResult.success(frontService.selectTagList());
    }

    /**
     * 查询轮播图
     * @return
     */
    @GetMapping("carousel")
    public BaseResult getCarousel() {
        return BaseResult.success(frontService.selectCarouselList());
    }

    /**
     * 查询通知
     * @return
     */
    @GetMapping("notice")
    public BaseResult getNotice() {
        return BaseResult.success(frontService.selectNoticeList());
    }

    /**
     * 根据qq好获取评论内容
     * @return
     */
    @GetMapping("comment/qqNum/{qqNum}")
    public BaseResult getByQQNum(@PathVariable Long qqNum) {
        String json = HttpUtils.sendGet(QQ_QUERY_URL, "uins=" + qqNum, "GBK");
        Map<String, String> qqInfo = new HashMap<>();
        if (!StringUtils.isEmpty(json)) {
            json = json.replaceAll("portraitCallBack|\\\\s*|\\t|\\r|\\n", "");
            json = json.substring(1, json.length() - 1);
            JSONObject object = JSON.parseObject(json);
            JSONArray array = object.getJSONArray(String.valueOf(qqNum));
            qqInfo.put("avatar", "https://q1.qlogo.cn/g?b=qq&nk=" + qqNum + "&s=40");
            qqInfo.put("email", qqNum + "@qq.com");
            qqInfo.put("nickName", array.getString(6));
        }
        return BaseResult.success(qqInfo);
    }

    /**
     * 新增评论
     * @param comment
     * @return
     */
    @PostMapping("comment")
    @VLog(module = "发表评论", pageId = "#comment.getPageId()")
    public BaseResult comment(@RequestBody Comment comment) {
        return toResult(frontService.insertComment(comment));
    }

    /**
     * 根据页面id获取评论
     * @param id id
     * @return
     */
    @GetMapping("/comment/{id}")
    public BaseResult commentBlog(@PathVariable Long id) {
        List<Comment> commentList = frontService.selectCommentListByPageId(id);
        return BaseResult.success(commentList);
    }

    @PutMapping("/comment/good/{id}")
    @VLog(module = "点赞评论")
    public BaseResult goodComment(@PathVariable Long id) {
        return toResult(frontService.incrementCommentGood(id));
    }

    @PutMapping("/comment/bad/{id}")
    @VLog(module = "踩评论")
    public BaseResult badComment(@PathVariable Long id) {
        return toResult(frontService.incrementCommentBad(id));
    }

    @PutMapping("/blog/like/{id}")
    @VLog(module = "点赞博客", pageId = "#id")
    public BaseResult likeBlog(@PathVariable Long id) {
        return BaseResult.success(frontService.incrementBlogLike(id));
    }

    @GetMapping("/blog/{id}")
    @VLog(module = "查看博客", pageId = "#id")
    public BaseResult blogDetail(@PathVariable Long id) {
        Blog blog = frontService.selectBlogDetailById(id);
        frontService.incrementBlogClick(id);
        return BaseResult.success(blog);
    }

    @GetMapping("/blog")
    @VLog(module = "首页")
    public TableDataInfo blog(BlogQuery blogQuery) {
        startPage();
        List<Blog> blogList = frontService.selectBlogList(blogQuery);
        return getDataTable(blogList);
    }

    @GetMapping("/frontBlog")
    @VLog(module = "首页")
    public TableDataInfo frontBlog(BlogQuery blogQuery) {
        startPage();
        List<Blog> blogList = frontService.selectBlogList(blogQuery);
        return getDataTable(blogList);
    }

    @GetMapping("/about")
    @VLog(module = "关于我")
    public BaseResult about() {
        return BaseResult.success(frontService.selectAbout());
    }

    @GetMapping("/archive")
    @VLog(module = "时光轴")
    public TableDataInfo archive(BlogQuery blogQuery) {
        startPage();
        List<Blog> blogList = frontService.selectBlogArchive(blogQuery);
        return getDataTable(blogList);
    }

}
