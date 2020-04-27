package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Comment;

import java.util.List;

/**
 * @author Maple
 * @description 评论mapper
 * @date 2020/2/5 18:17
 */
public interface CommentMapper extends Mapper<Comment> {

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    Comment selectCommentById(String commentId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param comment 实例对象
     * @return 对象列表
     */
    List<Comment> selectCommentList(Comment comment);

    /**
     * 新增数据
     *
     * @param comment 实例对象
     * @return 影响行数
     */
    int insertComment(Comment comment);

    /**
     * 修改数据
     *
     * @param comment 实例对象
     * @return 影响行数
     */
    int updateComment(Comment comment);

    /**
     * 通过主键删除数据
     *
     * @param ids       主键
     * @param username
     * @return 影响行数
     */
    int deleteCommentById(@Param("ids") String[] ids, @Param("username") String username);

    /**
     * 评论点赞
     *
     * @param commentId
     * @return 受影响的行数
     */
    int incrementCommentGood(String commentId);

    /**
     * 评论踩
     *
     * @param commentId
     * @return 受影响的行数
     */
    int incrementCommentBad(String commentId);

    /**
     * 获取博客对应的评论的Map
     *
     * @param blogIdList blog的Id
     * @return map
     */
    List<Comment> selectCommentListByPageIds(@Param("blogIdList") List<String > blogIdList);

    /**
     * 获取page id对应的comment
     *
     * @param commentId pageId
     * @return comment list
     */
    List<Comment> selectCommentListByPageId(String commentId);
}
