package top.maplefix.service.impl;

import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.CommentMapper;
import top.maplefix.model.Comment;
import top.maplefix.service.CommentService;
import top.maplefix.utils.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Maple
 * @description 评论service实现类
 * @date 2020/3/13 16:07
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private  CommentMapper commentMapper;

    @Override
    public List<Comment> selectCommentList(Comment comment) {
        List<Comment> commentList = commentMapper.selectCommentList(comment);
        if (!SecurityUtils.isAdmin()) {
            commentList.forEach(e->{
                if (StringUtils.isNotEmpty(e.getQqNum())) {
                    e.setQqNum(e.getQqNum().replaceAll("[1-9][0-9]{4,}","*"));
                }
                if (StringUtils.isNotEmpty(e.getEmail())) {
                    e.setEmail(e.getEmail().replaceAll("[1-9][0-9]{4,}", "*"));
                }
            });
        }
        return commentList;
    }

    @Override
    public Comment selectCommentById(Long id) {
        return commentMapper.selectCommentById(id);
    }

    @Override
    public int insertComment(Comment comment) {
        comment.setAdminReply(SecurityUtils.isAdmin());
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        comment.setOs(userAgent.getOperatingSystem().getName());
        comment.setBrowser(userAgent.getBrowser().getName());
        comment.setIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        comment.setLocation(AddressUtils.getRealAddressByIp(comment.getIp()));
        comment.setCreateDate(DateUtils.getTime());
        return commentMapper.insertComment(comment);
    }

    @Override
    public int updateComment(Comment comment) {
        return commentMapper.updateComment(comment);
    }

    @Override
    public int deleteCommentByIds(String ids) {
        return commentMapper.deleteCommentById(ConvertUtils.toStrArray(ids));
    }

    @Override
    public int incrementCommentGood(Long id) {
        return commentMapper.incrementCommentGood(id);
    }

    @Override
    public int incrementCommentBad(Long id) {
        return commentMapper.incrementCommentBad(id);
    }

    @Override
    public List<Comment> selectCommentListByPageId(Long id) {
        //查询获取所有的comment
        List<Comment> commentList = commentMapper.selectCommentListByPageId(id);
        List<Comment> result = commentList.stream().filter(e -> e.getParentId() == null).collect(Collectors.toList());
        //CommentId和NickName的映射Map
        Map<Long, String> commentIdAndNickNameMap = commentList.stream().collect(Collectors.toMap(Comment::getId, Comment::getNickName));
        for (Comment comment : result) {
            Long commentId = comment.getId();
            comment.setSubCommentList(commentList.stream().filter(e -> commentId.equals(e.getParentId())).collect(Collectors.toList()));
            //设置replyNickName
            if (ObjectUtils.isNotEmpty(comment.getSubCommentList())) {
                for (Comment temp : comment.getSubCommentList()) {
                    if (temp.getReplyId() != null) {
                        temp.setReplyNickName(commentIdAndNickNameMap.get(temp.getReplyId()));
                    }
                }
            }
        }
        return result;
    }
}
