package top.maplefix.service;

import top.maplefix.model.Comment;

import java.util.List;

/**
 * @author : Maple
 * @description : 博客评论接口
 * @date : 2020/1/15 15:46
 */
public interface CommentService {
    /**
     * 获取评论列表
     *
     * @param comment comment
     * @return List
     */
    List<Comment> selectCommentList(Comment comment);

    /**
     * 根据id获取comment
     *
     * @param id id
     * @return comment对象
     */
    Comment selectCommentById(Long id);

    /**
     * 插入comment
     *
     * @param comment comment对象
     * @return 受影响的行数
     */
    int insertComment(Comment comment);

    /**
     * 更新评论
     *
     * @param comment 评论
     * @return 受影响的行数
     */
    int updateComment(Comment comment);

    /**
     * 根据id删除comment
     *
     * @param ids  ids
     * @return 受影响的行数
     */
    int deleteCommentByIds(String ids);

    /**
     * 评论点赞
     *
     * @param id 评论的id
     * @return 受影响的行数
     */
    int incrementCommentGood(Long id);

    /**
     * 评论踩
     *
     * @param id comment id
     * @return 受影响的行数
     */
    int incrementCommentBad(Long id);

    /**
     * 根据PageId获取所有的评论
     *
     * @param id pageId
     * @return comment list
     */
    List<Comment> selectCommentListByPageId(Long id);
}
