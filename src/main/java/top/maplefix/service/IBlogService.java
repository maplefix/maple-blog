package top.maplefix.service;

import top.maplefix.model.Blog;
import top.maplefix.vo.Archive;
import top.maplefix.vo.CategoryBlog;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客表操作接口
 * @date : Created in 2019/7/24 22:37
           Edited in 2019/10/28 15:20
 * @version : v2.1
 */
public interface IBlogService {

    /**
     * 获取首页博列表数据
     * @param params
     * @return
     */
    List<Blog> selectBlogForIndexPage(Map<String, Object> params);
    /**
     * 根据id数组查询博客列表
     * @param ids 博客id
     * @return Blog
     */
    List<Blog> selectBlogByIds(String[] ids);
    /**
     * 查询最新或者最热博客列表
     * @param type 1：最新，2：最热
     * @return
     */
    List<Blog> selectBlogForNewestOrHottest(int type);

    /**
     * 查询首页最热分类
     * @return
     */
    List<CategoryBlog> selectBlogForHotCategory();
    /**
     * 获取已发布的博客总数
     * @return
     */
    int selectTotalBlog();

    /**
     * 根据条件插叙你单条博客记录
     * @param blog
     * @return
     */
    Blog getBlog(Blog blog);

    /**
     * 新增一条博客记录
     * @param blog
     * @return
     */
    boolean insert(Blog blog);

    /**
     * 根据id批量删除
     * @param ids
     */
    void deleteBatch(String[] ids);

    /**
     * 更新博客信息
     * @param blog
     * @return
     */
    void updateBlog(Blog blog);

    /**
     * 博客修改时判断标签是否有修改
     * @param blogId 博客名称
     * @param labelName 标签名
     * @return 标签名
     */
    String parseLabel(String blogId,String labelName);
    /**
     * 获取归档信息
     *
     * @return 归档集合
     */
    List<Archive> selectArchives();

    /**
     * 根据分类id查询该分类是否与博客关联
     * @param categoryIds 分类id数字
     * @return
     */
    boolean isExistBlogCategory(String[] categoryIds);
}

