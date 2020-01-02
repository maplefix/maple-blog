package top.maplefix.vo;

import lombok.Data;
import top.maplefix.model.Blog;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Maple
 * @description : 归档实体
 * @date : Created in 2019/9/14 21:05
 * @version : v2.1
 */
@Data
public class Archive implements Serializable {

    /**
     * 博客创建时间
     */
    private String createDate;
    /**
     * 总数
     */
    private Integer count;
    /**
     * 博客列表
     */
    private List<Blog> blogList;
}
