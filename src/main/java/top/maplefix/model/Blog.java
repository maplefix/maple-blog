package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Maple
 * @description : 博客表实体
 * @date : Created in 2019/7/23 22:14
 * @version : v2.1
 */
@Data
@Table(name = "t_blog")
@NameStyle
public class Blog implements Serializable {

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String blogId;
    /**
     * 博客标题
     */
    @Excel(name = "标题")
    private String title;
    /**
     * 封面相对路径
     */
    @Excel(name = "封面路径")
    private String coverImg;
    /**
     * 博客内容
     */
    @Excel(name = "内容")
    private String content;
    /**
     * 内容摘要
     */
    @Excel(name = "摘要")
    private String summary;
    /**
     * 分类id
     */
    private String categoryId;
    /**
     * 博客分类名称
     */
    @Transient
    @Excel(name = "分类")
    private String categoryName;
    /**
     * 博客标签，冗余字段
     */
    @Excel(name = "标签")
    private String label;

    @Transient
    private List<String> labels;
    /**
     * 博文状态，1表示已经发表，2表示在草稿箱，3表示在垃圾箱
     */
    @Excel(name = "状态" ,readConverterExp = "1=已发布,2=草稿箱,3=垃圾箱")
    private String status;
    /**
     * 点击量
     */
    @Excel(name = "点击量")
    private Integer hits;
    /**
     * 权重
     */
    @Excel(name = "权重")
    private Integer height;
    /**
     * 是否推荐(置顶)，1表示推荐，0表示不推荐
     */
    @Excel(name = "是否推荐",readConverterExp = "1=推荐,0=普通")
    private String support;
    /**
     * 删除标识(1:删除,0:正常)
     */
    @Excel(name = "是否删除",readConverterExp = "1=删除,0=正常")
    private String delFlag;
    /**
     * 原创标识(1:原创,0:转载)
     */
    @Excel(name = "是否原创",readConverterExp = "1=原创,0=转载")
    private String originalFlag;
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

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
