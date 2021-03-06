package top.maplefix.service;

import top.maplefix.model.*;
import top.maplefix.vo.BlogQuery;

import java.util.List;

/**
 * @author Maple
 * @description 博客前端service接口
 * @date 2020/3/13 9:56
 */
public interface FrontService {

    /**
     * 查询所有友链列表
     * @return list of link
     */
    List<Link> selectLinkList();

    /**
     * 新增友链申请
     * @param link link
     * @return 插入行数
     */
    int insertLink(Link link);

    /**
     * 根据推荐查询目录
     * @return list
     */
    List<Category> selectCategoryList();

    /**
     * 查询推荐博客列表
     * @return list
     */
    List<Blog> selectSupportBlogList();

    /**
     * 查询最热门博客
     * @return list
     */
    List<Blog> selectHotBlogList();

    /**
     * 查询标签列表
     * @return list
     */
    List<Tag> selectTagList();

    /**
     * 查询轮播图列表
     * @return list
     */
    List<Carousel> selectCarouselList();

    /**
     * 查询通知公告列表
     * @return list
     */
    List<Notice> selectNoticeList();

    /**
     * 新增评论
     * @param comment comment
     * @return 影响行数
     */
    int insertComment(Comment comment);

    /**
     * 根据pageId查询评论
     * @param id 页面id
     * @return 影响行数
     */
    List<Comment> selectCommentListByPageId(Long  id);

    /**
     *增长点赞评论
     * @param id 页面id
     * @return 影响行数
     */
    int incrementCommentGood(Long id);

    /**
     * 增长踩评论
     * @param id 页面id
     * @return 影响行数
     */
    int incrementCommentBad(Long id);

    /**
     * 增长博客喜欢
     * @param id 页面id
     * @return 影响行数
     */
    int incrementBlogLike(Long id);

    /**
     * 根据博客id获取博客详情
     * @param id 博客id
     * @return blog
     */
    Blog selectBlogDetailById(Long  id);

    /**
     * 条件查询博客列表
     * @param blogQuery
     * @return
     */
    List<Blog> selectBlogList(BlogQuery blogQuery);
    /**
     * 新增友链点击数
     * @param id id
     * @return
     */
    int incrementLinkClick(Long id);
    /**
     * 更新博客点击次数
     *
     * @param id blog id
     * @return update count
     */
    int incrementBlogClick(Long  id);
    /**
     * 从字段表查询关于我信息
     * @return
     */
    String selectAbout();


    /**
     * 查询博客归档
     * @param blogQuery 查询条件
     * @return list
     */
    List<Blog> selectBlogArchive(BlogQuery blogQuery);

    /**
     * 查询推荐的友链集合
     * @return list
     */
    List<Link> selectSupportLinkList();
}
