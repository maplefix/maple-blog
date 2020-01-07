package top.maplefix.vo;

import lombok.Data;

/**
 * @author : Maple
 * @description : 查询首页分类和博客数量bean
 * @date : Created in 2019/10/25 17:32
 * @version : v1.0
 */
@Data
public class CategoryBlog {

    /**
     * 分类id
     */
    private String categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 博客数量
     */
    private Integer blogCount;
}
