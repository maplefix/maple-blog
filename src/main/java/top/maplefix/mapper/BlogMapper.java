package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Blog;
import top.maplefix.vo.BlogQuery;

import java.util.List;

/**
 * @author : Maple
 * @description : 博客mapper
 * @date : 2020/3/3 0:29
 */
public interface BlogMapper extends Mapper<Blog>{

    /**
     * 查询博客
     *
     * @param id 博客ID
     * @return 博客
     */
    Blog selectBlogById(String id);

    /**
     * 查询博客列表
     *
     * @param blog 博客
     * @return 博客集合
     */
    List<Blog> selectBlogList(Blog blog);

    /**
     * 新增博客
     *
     * @param blog 博客
     * @return 结果
     */
    int insertBlog(Blog blog);

    /**
     * 修改博客
     *
     * @param blog 博客
     * @return 结果
     */
    int updateBlog(Blog blog);

    /**
     * 删除博客
     *
     * @param id 博客ID
     * @return 结果
     */
    int deleteBlogById(@Param("id") String id, @Param("username") String username);

    /**
     * 批量删除博客
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBlogByIds(@Param("ids") String[] ids, @Param("username") String username);

    /**
     * 前台查询blog
     *
     * @param blogQuery blog查询条件
     * @return list
     */
    List<Blog> selectBlogListQuery(BlogQuery blogQuery);

    /**
     * 新增blog的like
     *
     * @param id id
     * @return 受影响的行数
     */
    int incrementBlogLike(String id);

    /**
     * 增加blog的click数量
     *
     * @param id id
     */
    void incrementBlogClick(String id);

    /**
     * 查询博客
     *
     * @param id 博客ID
     * @return 博客
     */
    Blog selectBlogByIdQuery(String id);

    /**
     * 根据categoryId获取所有的blog
     *
     * @param ids category ids
     * @return blog list
     */
    List<Blog> selectBlogListByCategoryIds(@Param("ids") List<String> ids);
}
