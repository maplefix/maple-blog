package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.Constant;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.BlogMapper;
import top.maplefix.mapper.CategoryMapper;
import top.maplefix.model.Blog;
import top.maplefix.model.Category;
import top.maplefix.service.ICategoryService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.UuidUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客分类接口实现类
 * @date : Created in 2019/7/24 22:53
           Edited in 2019/10/30
 * @version : v2.1
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public int getTotalCategory() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        //分类状态未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);

        return categoryMapper.selectCountByExample(example);
    }

    @Override
    public List<Category> getAllCategory() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        //分类状态未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<Category> getBlogCategoryPage(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        String categoryName = top.maplefix.utils.StringUtils.getObjStr(params.get("categoryName"));
        String beginDate = top.maplefix.utils.StringUtils.getObjStr(params.get("beginDate"));
        String endDate = top.maplefix.utils.StringUtils.getObjStr(params.get("endDate"));
        Example example = new Example(Category.class);
        //根据时间排序
        example.setOrderByClause("createDate desc");
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(categoryName)){
            criteria.andEqualTo("categoryName", categoryName);
        }
        if(!StringUtils.isEmpty(beginDate)){
            criteria.andGreaterThan("createDate", beginDate + " 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andLessThan("createDate", endDate + " 23:59:59");
        }
        //未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        PageHelper.startPage(currPage, pageSize);
        List<Category> categoryList = categoryMapper.selectByExample(example);
        //设置每个分类关联的博客数量
        for (Category category : categoryList){
            Blog blog = new Blog();
            blog.setCategoryId(category.getCategoryId());
            int count = blogMapper.selectCount(blog);
            category.setCount(count);
        }
        return categoryList;
    }

    @Override
    public Category isExistCategory(Category category) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryName", category.getCategoryName());
        //未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        return categoryMapper.selectOneByExample(example);

    }

    @Override
    public void saveCategory(Category category) {
        category.setCategoryId(UuidUtils.getRandomUuidWithoutSeparator());
        category.setDelFlag(Constant.NORMAL);
        category.setCreateDate(DateUtils.getCurrDate());
        categoryMapper.insert(category);
    }

    @Override
    public void updateCategory(Category category) {
        category.setUpdateDate(DateUtils.getCurrDate());
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void deleteBatch(String[] catIds) {
        for (String catId : catIds){
            Category category = new Category();
            category.setCategoryId(catId);
            category.setDelFlag(Constant.DELETED);
            categoryMapper.updateByPrimaryKeySelective(category);
        }
    }

    @Override
    public List<Category> selectCategoryByIds(String[] ids) {
        //如果前端没选中列表数据则全部导出
        if(null == ids || ids.length == 0){
            Example example = new Example(Category.class);
            example.setOrderByClause("createDate desc");
            Example.Criteria criteria = example.createCriteria();
            //未删除
            criteria.andEqualTo("delFlag", Constant.NORMAL);
            return categoryMapper.selectByExample(example);
        }
        //将数组转成字符串，用逗号隔开
        String idsStr = StringUtils.join(ids,",");
        return categoryMapper.selectByIds(idsStr);
    }
}
