package top.maplefix.service.impl;

import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.maplefix.config.factory.AsyncFactory;
import top.maplefix.config.factory.AsyncManager;
import top.maplefix.constant.CacheConstants;
import top.maplefix.constant.Constant;
import top.maplefix.enums.TagType;
import top.maplefix.mapper.BlogFrontMapper;
import top.maplefix.model.*;
import top.maplefix.redis.CacheExpire;
import top.maplefix.redis.TimeType;
import top.maplefix.service.BlogFrontService;
import top.maplefix.utils.AddressUtils;
import top.maplefix.utils.IpUtils;
import top.maplefix.utils.SecurityUtils;
import top.maplefix.utils.ServletUtils;
import top.maplefix.vo.BlogQuery;
import top.maplefix.vo.ReplayEmail;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Maple
 * @description 博客前端service实现类
 * @date 2020/3/13 10:21
 */
@Service
public class BlogFrontServiceImpl implements BlogFrontService {

    @Autowired
    private BlogFrontMapper blogFrontMapper;


    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_LINK_LIST)
    @CacheExpire(expire = 1,type = TimeType.HOURS)
    public List<Link> selectLinkList() {
        return blogFrontMapper.selectLinkList();
    }

    @Override
    public int insertLink(Link link) {
        link.setStatus(Constant.FAILED);
        link.setDisplay(Constant.DISPLAY);
        return blogFrontMapper.insertLink(link);
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_CATEGORY_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Category> selectCategoryList() {
        return blogFrontMapper.selectCategoryList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_SUPPORT_BLOG_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Blog> selectSupportBlogList() {
        return blogFrontMapper.selectSupportBlogList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_HOT_BLOG_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Blog> selectHotBlogList() {
        return blogFrontMapper.selectHotBlogList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_TAG_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Tag> selectTagList() {
        return blogFrontMapper.selectTagList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_CAROUSEL_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Carousel> selectCarouselList() {
        return blogFrontMapper.selectCarouselList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_NOTICE_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Notice> selectNoticeList() {
        return blogFrontMapper.selectNoticeList();
    }

    @Override
    public int insertComment(Comment comment) {
        comment.setAdminReply(SecurityUtils.isAdmin()?1:0);
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        comment.setOs(userAgent.getOperatingSystem().getName());
        comment.setBrowser(userAgent.getBrowser().getName());
        comment.setDisplay(Constant.DISPLAY);
        comment.setIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        comment.setLocation(AddressUtils.getRealAddressByIp(comment.getIp()));
        if (comment.getParentId() != null) {
            Comment tempComment = blogFrontMapper.selectCommentById(comment.getParentId());
            String title = blogFrontMapper.selectBlogTitleById(comment.getPageId());
            if (tempComment.getReply().equals(Constant.SUCCESS)) {
                ReplayEmail replayEmail = new ReplayEmail();
                replayEmail.setCreateDate(tempComment.getCreateDate());
                replayEmail.setOriginContent(tempComment.getHtmlContent());
                replayEmail.setReplyContent(comment.getHtmlContent());
                replayEmail.setUrl(comment.getUrl());
                replayEmail.setTitle(title);
                AsyncManager.me().execute(AsyncFactory.sendReplyEmail(comment.getUrl(), comment.getHtmlContent(), comment.getNickName(), tempComment.getEmail(), replayEmail));
            }
        }
        return blogFrontMapper.insertComment(comment);
    }


    @Override
    public List<Comment> selectCommentListByPageId(String  id) {
        //查询获取所有的comment
        List<Comment> commentList = blogFrontMapper.selectCommentListByPageId(id);
        List<Comment> result = commentList.stream().filter(e -> e.getParentId() == null).collect(Collectors.toList());
        //CommentId和NickName的映射Map
        Map<String, String> commentIdAndNickNameMap = commentList.stream().collect(Collectors.toMap(Comment::getCommentId, Comment::getNickName));
        for (Comment comment : result) {
            String commentId = comment.getCommentId();
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

    @Override
    public int incrementCommentGood(String id) {
        return blogFrontMapper.incrementCommentGood(id);
    }

    @Override
    public int incrementCommentBad(String id) {
        return blogFrontMapper.incrementCommentBad(id);
    }

    @Override
    public int incrementBlogLike(String id) {
        return blogFrontMapper.incrementBlogLike(id);
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_BLOG_ITEM, key = "'BlogId:' +#id")
    @CacheExpire(expire = 1, type = TimeType.MINUTES)
    public Blog selectBlogDetailById(String id) {
        Blog blog = blogFrontMapper.selectBlogDetailById(id);
        //get all comment
        blog.setCommentList(selectCommentListByPageId(id));
        return blog;
    }

    @Override
    public List<Blog> selectBlogList(BlogQuery blogQuery) {
        List<Blog> blogList = blogFrontMapper.selectBlogList(blogQuery);
        for (Blog blog : blogList) {
            blog.setTagList(blogFrontMapper.selectTagListByTypeAndId(TagType.BLOG.getType(), blog.getBlogId()));
        }
        return blogList;
    }

    @Override
    public int incrementLinkClick(String  id) {
        return blogFrontMapper.incrementLinkClick(id);
    }

    @Override
    public int incrementBlogClick(String id) {
        return blogFrontMapper.incrementBlogClick(id);
    }

    @Override
    public String selectAbout() {
        DictData dictData = blogFrontMapper.selectAbout();
        return dictData.getDictValue();
    }

    @Override
    public List<Blog> selectBlogArchive(BlogQuery blogQuery) {
        List<Blog> blogList = blogFrontMapper.selectBlogList(blogQuery);
        for (Blog blog : blogList) {
            blog.setTagList(blogFrontMapper.selectTagListByTypeAndId(TagType.BLOG.getType(), blog.getBlogId()));
        }
        return blogList;
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_SUPPORT_LINK_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Link> selectSupportLinkList() {
        return blogFrontMapper.selectSupportLinkList();
    }
}
