package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.maplefix.annotation.Excel;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Maple
 * @description : 博客分类实体
 * @date : 2020/1/15 14:39
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category extends BaseEntity implements Serializable {

    /**
     * 分类主键
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String title;

    /**
     * 是否推荐
     */
    //@NotNull(message = "推荐设置不能为空")
    private Boolean support;
    /**
     * 描述信息
     */
    @Excel(name = "描述")
    private String description;

    private List<Blog> blogList;
}
