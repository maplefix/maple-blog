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
import top.maplefix.mapper.FrontMapper;
import top.maplefix.model.*;
import top.maplefix.redis.CacheExpire;
import top.maplefix.redis.TimeType;
import top.maplefix.service.FrontService;
import top.maplefix.utils.*;
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
public class FrontServiceImpl implements FrontService {

    @Autowired
    private FrontMapper frontMapper;


    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_LINK_LIST)
    @CacheExpire(expire = 1,type = TimeType.HOURS)
    public List<Link> selectLinkList() {
        return frontMapper.selectLinkList();
    }

    @Override
    public int insertLink(Link link) {
        link.setStatus(Constant.FAILED);
        link.setDisplay(Constant.DISPLAY);
        return frontMapper.insertLink(link);
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_CATEGORY_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Category> selectCategoryList() {
        return frontMapper.selectCategoryList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_SUPPORT_BLOG_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Blog> selectSupportBlogList() {
        return frontMapper.selectSupportBlogList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_HOT_BLOG_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Blog> selectHotBlogList() {
        return frontMapper.selectHotBlogList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_TAG_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Tag> selectTagList() {
        return frontMapper.selectTagList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_CAROUSEL_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Carousel> selectCarouselList() {
        return frontMapper.selectCarouselList();
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_NOTICE_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Notice> selectNoticeList() {
        return frontMapper.selectNoticeList();
    }

    @Override
    public int insertComment(Comment comment) {
        comment.setAdminReply(SecurityUtils.isAdmin());
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        comment.setOs(userAgent.getOperatingSystem().getName());
        comment.setBrowser(userAgent.getBrowser().getName());
        comment.setDisplay(Constant.DISPLAY);
        comment.setIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        comment.setLocation(AddressUtils.getCityInfoByIp(comment.getIp()));
        comment.setCreateDate(DateUtils.getTime());
        if (comment.getParentId() != null) {
            Comment tempComment = frontMapper.selectCommentById(comment.getParentId());
            String title = frontMapper.selectBlogTitleById(comment.getPageId());
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
        return frontMapper.insertComment(comment);
    }


    @Override
    public List<Comment> selectCommentListByPageId(Long id) {
        //查询获取所有的comment
        List<Comment> commentList = frontMapper.selectCommentListByPageId(id);
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

    @Override
    public int incrementCommentGood(Long id) {
        return frontMapper.incrementCommentGood(id);
    }

    @Override
    public int incrementCommentBad(Long id) {
        return frontMapper.incrementCommentBad(id);
    }

    @Override
    public int incrementBlogLike(Long id) {
        return frontMapper.incrementBlogLike(id);
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_BLOG_ITEM, key = "'BlogId:' +#id")
    @CacheExpire(expire = 1, type = TimeType.MINUTES)
    public Blog selectBlogDetailById(Long id) {
        Blog blog = frontMapper.selectBlogDetailById(id);
        //get all comment
        blog.setCommentList(selectCommentListByPageId(id));
        return blog;
    }

    @Override
    public List<Blog> selectBlogList(BlogQuery blogQuery) {
        List<Blog> blogList = frontMapper.selectBlogList(blogQuery);
        for (Blog blog : blogList) {
            blog.setTagList(frontMapper.selectTagListByTypeAndId(blog.getId()));
        }
        return blogList;
    }

    @Override
    public int incrementLinkClick(Long  id) {
        return frontMapper.incrementLinkClick(id);
    }

    @Override
    public int incrementBlogClick(Long id) {
        return frontMapper.incrementBlogClick(id);
    }

    @Override
    public String selectAbout() {
        DictData dictData = frontMapper.selectAbout();
        return dictData.getDictValue();
    }

    @Override
    public List<Blog> selectBlogArchive(BlogQuery blogQuery) {
        List<Blog> blogList = frontMapper.selectBlogList(blogQuery);
        for (Blog blog : blogList) {
            blog.setTagList(frontMapper.selectTagListByTypeAndId(blog.getId()));
        }
        return blogList;
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_FRONT_SUPPORT_LINK_LIST)
    @CacheExpire(expire = 1, type = TimeType.HOURS)
    public List<Link> selectSupportLinkList() {
        return frontMapper.selectSupportLinkList();
    }
}
