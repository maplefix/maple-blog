package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.aggregation.AggregationMapper;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Blog;
import top.maplefix.vo.Archive;
import top.maplefix.vo.CategoryBlog;

import java.util.List;

/**
 * @author : Maple
 * @description : 博客mapper
 * @date : Created in 2019/7/25 0:29
 * @editor: Edited in 2019/10/28 15:15
 * @version: v2.1
 */
@CacheNamespace
public interface BlogMapper extends Mapper<Blog>, AggregationMapper<Blog> {

    /**
     * 查询前台首页博客列表
     * @param title 标题
     * @param categoryId 分类id
     * @param label 标签名
     * @param keyword 前台查询关键字
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param isAll 是否查出所有状态的文章
     * @return 博客列表
     */
    List<Blog> selectBlogForIndexPage(@Param("title") String title, @Param("categoryId") String categoryId,
                                   @Param("label") String label, @Param("keyword") String keyword,
                                   @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                   @Param("isAll") String isAll);

    /**
     * 根据id批量查询博客列表
     * @param blogIds 博客id集合
     * @return Blog
     */
    List<Blog> selectBlogByIds(@Param("blogIds") String[] blogIds);
    /**
     * 获取归档的Date和count
     *
     * @return list集合
     */
    List<Archive> selectArchiveDateAndCount();

    /**
     * 根据createTime获取blog信息
     *
     * @param createDate 创建的时间
     * @return blog集合
     */
    List<Blog> selectBlogByCreateTime(@Param("createDate") String createDate);

    /**
     * 首页最热门分类查询
     * @return
     */
    List<CategoryBlog> selectBlogForHotCategory();
}
