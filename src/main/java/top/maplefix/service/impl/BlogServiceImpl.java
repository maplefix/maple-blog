package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.maplefix.constant.CacheConstants;
import top.maplefix.enums.TagType;
import top.maplefix.mapper.BlogMapper;
import top.maplefix.mapper.CommentMapper;
import top.maplefix.model.Blog;
import top.maplefix.model.Comment;
import top.maplefix.model.Tag;
import top.maplefix.service.BlogService;
import top.maplefix.service.TagService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.SecurityUtils;
import top.maplefix.vo.BlogQuery;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Maple
 * @description : 博客接口实现类
 * @date : 2019/7/24 22:37
           Edited in 2019/10/28 14:15
 * @version : v1.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Blog selectBlogById(String id) {
        Blog blog = blogMapper.selectBlogById(id);
        blog.setTagTitleList(getTagTitleListByBlogId(id));
        return blog;
    }

    @Override
    public List<Blog> selectBlogList(Blog blog) {
        List<Blog> blogList = blogMapper.selectBlogList(blog);
        if (blogList.isEmpty()) {
            return blogList;
        }
        List<String> blogIdList = blogList.stream().map(Blog::getBlogId).collect(Collectors.toList());
        //设置comment信息
        List<Comment> commentList = commentMapper.selectCommentListByPageIds(blogIdList);
        for (Blog temp : blogList) {
            temp.setCommentList(commentList.stream().filter(e -> e.getPageId().equals(temp.getBlogId())).collect(Collectors.toList()));
            temp.setTagTitleList(getTagTitleListByBlogId(temp.getBlogId()));
        }
        return blogList;
    }

    /**
     * 根据id获取title集合
     *
     * @param blogId blog的id
     * @return title集合
     */
    private List<String> getTagTitleListByBlogId(String blogId) {
        List<Tag> tagList = tagService.selectTagListByTypeAndId(TagType.BLOG.getType(), blogId);
        return tagList.stream().map(Tag::getTagName).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'")
    public int insertBlog(Blog blog) {
        blog.setCreateBy(SecurityUtils.getUsername());
        int count = blogMapper.insertBlog(blog);
        tagService.updateTagMid(TagType.BLOG.getType(), blog.getBlogId(), blog.getTagTitleList());
        return count;
    }


    @Override
    @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'")
    public int updateBlog(Blog blog) {
        blog.setUpdateBy(SecurityUtils.getUsername());
        int count = blogMapper.updateBlog(blog);
        tagService.updateTagMid(TagType.BLOG.getType(), blog.getBlogId(), blog.getTagTitleList());
        return count;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'"),
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'HotList'"),
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'SupportList'"),
    })
    @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'")
    public int deleteBlogByIds(String ids) {
        String username = SecurityUtils.getUsername();
        return blogMapper.deleteBlogByIds(ConvertUtils.toStrArray(ids), username);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'"),
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'HotList'"),
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'SupportList'"),
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogItem_'+#id")
    })
    public int deleteBlogById(String id) {
        String username = SecurityUtils.getUsername();
        return blogMapper.deleteBlogById(id, username);
    }

    @Override
    @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'")
    public List<String> selectBlogTagList(String query) {
        Tag tag = new Tag();
        tag.setTagName(query);
        List<Tag> tagList = tagService.selectTagList(tag);
        return tagList.stream().map(Tag::getTagName).collect(Collectors.toList());
    }

    @Override
    public List<Blog> selectBlogList(BlogQuery blogQuery) {
        List<Blog> blogList = blogMapper.selectBlogListQuery(blogQuery);
        for (Blog blog : blogList) {
            blog.setTagList(tagService.selectTagListByTypeAndId(TagType.BLOG.getType(), blog.getBlogId()));
        }
        return blogList;
    }

    @Override
    public Blog selectBlogDetailById(String id) {
        Blog blog = blogMapper.selectBlogByIdQuery(id);
        blog.setTagList(tagService.selectTagListByTypeAndId(TagType.BLOG.getType(), id));
        //获取commentList
        blog.setCommentList(commentMapper.selectCommentListByPageId(id));
        //设置点击数量+1
        blogMapper.incrementBlogClick(id);
        return blog;
    }

    /**
     * 获取评论
     *
     * @param id id
     * @return 评论列表
     */
    private List<Comment> getCommentListByPageId(String id) {
        List<Comment> commentList = commentMapper.selectCommentListByPageId(id);
        for (Comment comment : commentList) {
            if (comment.getParentId() != null) {
                comment.setParentComment(commentMapper.selectCommentById(comment.getParentId()));
            }
        }
        return commentList;
    }

    @Override
    public int incrementBlogLike(String id) {
        return blogMapper.incrementBlogLike(id);
    }

    @Override
    public List<Comment> selectBlogCommentListByBlogId(String id) {
        return getCommentListByPageId(id);
    }

}
