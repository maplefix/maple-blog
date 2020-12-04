package top.maplefix.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.BlogMapper;
import top.maplefix.mapper.CategoryMapper;
import top.maplefix.model.Blog;
import top.maplefix.model.Category;
import top.maplefix.service.CategoryService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.DateUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Maple
 * @description : 博客分类接口实现类
 * @date : 2020/3/1 22:53
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Category selectCategoryById(Long id) {
        return categoryMapper.selectCategoryById(id);
    }

    @Override
    public List<Category> selectCategoryList(Category bgCategory) {
        List<Category> categoryList = categoryMapper.selectCategoryList(bgCategory);
        List<Long> categoryIds = categoryList.stream().map(Category::getId).collect(Collectors.toList());
        if (ObjectUtils.isEmpty(categoryIds)) {
            return categoryList;
        }
        List<Blog> blogList = blogMapper.selectBlogListByCategoryIds(categoryIds);
        for (Category category : categoryList) {
            List<Blog> collect = blogList.stream().filter(e -> category.getId().equals(e.getCategoryId())).collect(Collectors.toList());
            category.setBlogList(collect);
        }
        return categoryList;
    }

    @Override
    public int insertCategory(Category bgCategory) {
        bgCategory.setCreateDate(DateUtils.getTime());
        return categoryMapper.insertCategory(bgCategory);
    }

    @Override
    public int updateCategory(Category bgCategory) {

        bgCategory.setUpdateDate(DateUtils.getTime());
        return categoryMapper.updateCategory(bgCategory);
    }

    @Override
    public int deleteCategoryByIds(String ids) {
        return categoryMapper.deleteCategoryByIds(ConvertUtils.toLongArray(ids));
    }

    @Override
    public int deleteCategoryById(Long id) {
        return categoryMapper.deleteCategoryById(id);
    }

    @Override
    public List<Category> selectSupportCategory() {
        return categoryMapper.selectSupportBlogCategoryList();
    }
}
