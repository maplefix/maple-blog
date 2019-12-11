package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.BlogConstant;
import top.maplefix.constant.Constant;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.BlogLabelMapper;
import top.maplefix.mapper.BlogMapper;
import top.maplefix.mapper.LabelMapper;
import top.maplefix.model.Blog;
import top.maplefix.model.BlogLabel;
import top.maplefix.model.Label;
import top.maplefix.service.IBlogService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.Archive;
import top.maplefix.vo.CategoryBlog;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客接口实现类
 * @date : Created in 2019/7/24 22:37
 * @editor: Edited in 2019/10/28 14:15
 * @version: v2.1
 */
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private BlogLabelMapper blogLabelMapper;

    @Override
    public List<Blog> getBlogForIndexPage(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        //分类id
        String categoryId = top.maplefix.utils.StringUtils.getObjStr(params.get("categoryId"));
        //标题
        String title = top.maplefix.utils.StringUtils.getObjStr(params.get("title"));
        //标签名
        String labelName = top.maplefix.utils.StringUtils.getObjStr(params.get("labelName"));
        //检索关键字
        String keyword = top.maplefix.utils.StringUtils.getObjStr(params.get("keyword"));
        //开始时间
        String beginDate = top.maplefix.utils.StringUtils.getObjStr(params.get("beginDate"));
        //结束时间
        String endDate = top.maplefix.utils.StringUtils.getObjStr(params.get("endDate"));
        //后端查询查出所有状态的文章，包括删除和草稿箱的文章
        String isAll = top.maplefix.utils.StringUtils.getObjStr(params.get("isAll"));
        PageHelper.startPage(currPage, pageSize);
        List<Blog> blogList = blogMapper.getBlogForIndexPage(title, categoryId, labelName, keyword, beginDate, endDate,isAll);
        return blogList;
    }

    @Override
    public List<Blog> getBlogForNewestOrHottest(int type) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        if(type == BlogConstant.BLOG_NEWEST){
            //根据时间排序
            example.setOrderByClause("createDate desc");
        }else if (type == BlogConstant.BLOG_HOTTEST){
            //根据点击量排序
            example.setOrderByClause("hits desc");
        }
        //博客状态未已发布且未删除
        criteria.andEqualTo("status", BlogConstant.BLOG_PUBLISHED);
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        PageHelper.startPage(1, 6);
        return blogMapper.selectByExample(example);

    }

    @Override
    public List<CategoryBlog> getBlogForHotCategory() {
        return blogMapper.getBlogForHotCategory();
    }

    @Override
    public int getTotalBlog() {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        //博客状态未已发布且未删除
        criteria.andEqualTo("status", BlogConstant.BLOG_PUBLISHED);
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        return blogMapper.selectCountByExample(example);
    }

    @Override
    public Blog getBlog(Blog blog) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("blogId", blog.getBlogId());
        return blogMapper.selectOneByExample(example);
    }

    /**
     * 逻辑：先将博客表插入，然后插入标签表和标签博客关联表
     * @param blog
     * @return
     */
    @Override
    public boolean insert(Blog blog) {
        blog.setCreateDate(DateUtils.getCurrDate());
        blog.setDelFlag(Constant.NORMAL);
        int i = blogMapper.insert(blog);
        handlerBlogTag(blog.getBlogId(), blog.getLabel().split(","));
        return true;
    }

    /**
     * 插入数据到标签表和标签博客关联表
     * @apiNote 如果标签表里面存在名称相同的标签，则将已存在的标签id再插入到关联表中接口
     * @param blogId 博客id
     * @param labelName 标签名
     */
    private void handlerBlogTag(String  blogId, String[] labelName) {
        if (labelName == null || labelName.length == 0) {
            return;
        }
        //将标签数据插入数据库
        for(int i= 0; i<labelName.length; i++){
            Label label = new Label();
            label.setDelFlag(Constant.NORMAL);
            label.setLabelName(labelName[i]);
            /*if(labelMapper.selectOne(label) != null){
                continue;
            }*/
            Label localLabel = labelMapper.selectOne(label);
            //标签已经存在的话拿到labelId插入一条关联关系到博客标签关联表即可
            if(null != localLabel){
                //插入博客标签关联表
                BlogLabel blogLabel = new BlogLabel();
                blogLabel.setBlogId(blogId);
                blogLabel.setLabelId(localLabel.getLabelId());
                blogLabel.setCreateDate(DateUtils.getCurrDate());
                blogLabelMapper.insert(blogLabel);
            }else {
                //插入标签表
                label.setCreateDate(DateUtils.getCurrDate());
                labelMapper.insert(label);
                //插入博客标签关联表
                BlogLabel blogLabel = new BlogLabel();
                blogLabel.setBlogId(blogId);
                blogLabel.setLabelId(labelMapper.selectOne(label).getLabelId());
                blogLabel.setCreateDate(DateUtils.getCurrDate());
                blogLabelMapper.insert(blogLabel);
            }
        }
    }
    @Override
    public void deleteBatch(String[] ids) {
        for (String id : ids){
            Blog blog = new Blog();
            blog.setBlogId(id);
            blog.setDelFlag(Constant.DELETED);
            //状态改为在垃圾箱
            blog.setStatus(BlogConstant.BLOG_DUSTBIN);
            blogMapper.updateByPrimaryKeySelective(blog);
        }
    }

    @Override
    public void updateBlog(Blog blog) {
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    /**
     *  select date_format(create_time, '%Y-%m') as date,count(*) as count from bg_blog
     *         where status=1 group by date_format(create_time,'%Y-%m')order by date_format(create_time, '%Y-%m') desc
     *
     *         select blog_id,title,click,create_time from bg_blog
     *         where date_format(create_time, '%Y-%m') =#{date}
     * @return
     */
    @Override
    public List<Archive> selectArchives() {
        List<Archive> archives = blogMapper.selectArchiveDateAndCount();
        for (Archive archive : archives) {
            archive.setBlogList(blogMapper.selectBlogByCreateTime(archive.getCreateDate()));
        }
        return archives;
    }

    @Override
    public boolean isExistBlogCategory(String[] categoryIds) {
        for (String categoryId : categoryIds){
            Example example = new Example(Blog.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("categoryId", categoryId);
            List<Blog> labelList = blogMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(labelList)){
                return true;
            }
        }
        return false;
    }

}
