package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Category;

import java.util.List;

/**
 * @author : Maple
 * @description : 博客分类mapper
 * @date : 2019/7/25 0:30
 * @version : v1.0
 */
public interface CategoryMapper extends Mapper<Category>{

    /**
     * 查询分类
     *
     * @param id 分类ID
     * @return 分类
     */
    Category selectCategoryById(String id);

    /**
     * 查询分类列表
     *
     * @param category 分类
     * @return 分类集合
     */
    List<Category> selectCategoryList(Category category);

    /**
     * 新增分类
     *
     * @param category 分类
     * @return 结果
     */
    int insertCategory(Category category);

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    int updateCategory(Category category);

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 结果
     */
    int deleteCategoryById(@Param("id") String id);

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCategoryByIds(@Param("ids") String[] ids);

    /**
     * 获取所有的Blog Category support的分类
     *
     * @return list
     */
    List<Category> selectSupportBlogCategoryList();
}
