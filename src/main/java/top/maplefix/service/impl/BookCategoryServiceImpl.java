package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.enums.CategoryType;
import top.maplefix.mapper.CategoryMapper;
import top.maplefix.model.Category;
import top.maplefix.service.BookCategoryService;
import top.maplefix.utils.ConvertUtils;

import java.util.List;

/**
 * @author Maple
 * @description 图书目录service实现
 * @date 2020/3/20 11:11
 */
@Service
public class BookCategoryServiceImpl implements BookCategoryService {
    
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Category selectBookCategoryById(String id) {
        return categoryMapper.selectCategoryById(id);
    }

    @Override
    public List<Category> selectBookCategoryList(Category category) {
        category.setType(CategoryType.BOOK.getType());
        return categoryMapper.selectCategoryList(category);
    }

    @Override
    public int insertBookCategory(Category category) {
        category.setType(CategoryType.BOOK.getType());
        return categoryMapper.insertCategory(category);
    }

    @Override
    public int updateBookCategory(Category category) {
        return categoryMapper.updateCategory(category);
    }

    @Override
    public int deleteBookCategoryByIds(String ids) {
        return categoryMapper.deleteCategoryByIds(ConvertUtils.toStrArray(ids));
    }
}
