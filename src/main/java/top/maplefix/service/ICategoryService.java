package top.maplefix.service;

import top.maplefix.model.Category;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客分类接口
 * @date : Created in 2019/7/24 22:53
 * @editor:
 * @version: v2.1
 */
public interface ICategoryService {
    /**
     * 获取分类总数
     * @return
     */
    int getTotalCategory();

    /**
     * 查询所有分类列表
     * @return
     */
    List<Category> getAllCategory();

    /**
     * 分页查询分类数据
     * @param params
     * @return
     */
    List<Category> getBlogCategoryPage(Map<String, Object> params);

    /**
     * 查询该分类是否已存在
     * @param category
     * @return
     */
    Category isExistCategory(Category category);

    /**
     * 保存分类
     * @param category
     */
    void saveCategory(Category category);

    /**
     * 修改分类
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 根据id批量删除
     * @param catIds
     */
    void deleteBatch(String[] catIds);
}
