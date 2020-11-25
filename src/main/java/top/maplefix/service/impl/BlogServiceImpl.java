package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.maplefix.constant.CacheConstants;
import top.maplefix.mapper.BlogMapper;
import top.maplefix.mapper.CommentMapper;
import top.maplefix.model.Blog;
import top.maplefix.model.Comment;
import top.maplefix.model.Tag;
import top.maplefix.service.BlogService;
import top.maplefix.service.TagService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.DateUtils;
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
    public Blog selectBlogById(Long id) {
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
        //设置comment信息
        for (Blog temp : blogList) {
            temp.setTagTitleList(getTagTitleListByBlogId(temp.getId()));
        }
        return blogList;
    }

    /**
     * 根据id获取title集合
     *
     * @param blogId blog的id
     * @return title集合
     */
    private List<String> getTagTitleListByBlogId(Long blogId) {
        List<Tag> tagList = tagService.selectTagListByBlogId(blogId);
        return tagList.stream().map(Tag::getTitle).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'")
    public int insertBlog(Blog blog) {
        blog.setCreateBy(SecurityUtils.getUsername());
        blog.setCreateDate(DateUtils.getTime());
        int count = blogMapper.insertBlog(blog);
        tagService.updateTagMid(blog.getId(), blog.getTagTitleList());
        return count;
    }


    @Override
    @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'")
    public int updateBlog(Blog blog) {
        blog.setUpdateBy(SecurityUtils.getUsername());
        int count = blogMapper.updateBlog(blog);
        tagService.updateTagMid(blog.getId(), blog.getTagTitleList());
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
        return blogMapper.deleteBlogByIds(ConvertUtils.toLongArray(ids));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'"),
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'HotList'"),
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'SupportList'"),
            @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogItem_'+#id")
    })
    public int deleteBlogById(Long id) {
        return blogMapper.deleteBlogById(id);
    }

    @Override
    @CacheEvict(value = CacheConstants.CACHE_NAME_FRONT, key = "'BlogList'")
    public List<String> selectBlogTagList(String query) {
        Tag tag = new Tag();
        tag.setTitle(query);
        List<Tag> tagList = tagService.selectTagList(tag);
        return tagList.stream().map(Tag::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<Blog> selectBlogList(BlogQuery blogQuery) {
        List<Blog> blogList = blogMapper.selectBlogListQuery(blogQuery);
        for (Blog blog : blogList) {
            blog.setTagList(tagService.selectTagListByBlogId(blog.getId()));
        }
        return blogList;
    }

    @Override
    public Blog selectBlogDetailById(Long id) {
        Blog blog = blogMapper.selectBlogByIdQuery(id);
        blog.setTagList(tagService.selectTagListByBlogId(id));
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
    private List<Comment> getCommentListByPageId(Long id) {
        List<Comment> commentList = commentMapper.selectCommentListByPageId(id);
        for (Comment comment : commentList) {
            if (comment.getParentId() != null) {
                comment.setParentComment(commentMapper.selectCommentById(comment.getParentId()));
            }
        }
        return commentList;
    }

    @Override
    public int incrementBlogLike(Long id) {
        return blogMapper.incrementBlogLike(id);
    }

    @Override
    public List<Comment> selectBlogCommentListByBlogId(Long id) {
        return getCommentListByPageId(id);
    }

}
