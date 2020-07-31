package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.maplefix.annotation.Excel;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Maple
 * @description : 博客分类实体
 * @date : 2020/1/15 14:39
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "t_category")
public class Category implements Serializable {

    /**
     * 分类主键
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String categoryName;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private String createDate;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    private String updateDate;
    /**
     * 描述信息
     */
    @Excel(name = "描述")
    private String description;

    private List<Blog> blogList;
}
