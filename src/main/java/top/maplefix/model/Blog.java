package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
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
 * @editor:
 * @version: v2.1
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
    private String blogId;
    /**
     * 博客标题
     */
    private String title;
    /**
     * 封面相对路径
     */
    private String coverImg;
    /**
     * 博客内容
     */
    private String content;
    /**
     * 内容摘要
     */
    private String summary;
    /**
     * 分类id
     */
    private String categoryId;
    /**
     * 博客分类名称
     */
    @Transient
    private String categoryName;
    /**
     * 博客标签，冗余字段
     */
    private String label;

    @Transient
    private List<String> labels;
    /**
     * 博客状态，1表示已经发表，2表示在草稿箱
     */
    private String status;
    /**
     * 点击量
     */
    private Integer hits;
    /**
     * 权重
     */
    private Integer height;
    /**
     * 是否推荐(置顶)，1表示推荐，0表示不推荐
     */
    private String support;
    /**
     * 删除标识(1:删除,0:正常)
     */
    private String delFlag;
    /**
     * 原创标识(1:原创,0:转载)
     */
    private String originalFlag;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
