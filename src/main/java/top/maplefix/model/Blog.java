package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.maplefix.annotation.Excel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Maple
 * @description : 博客表实体,不序列化null列
 * @date : 2020/1/15 14:26
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Blog extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 分类id
     */
    @NotNull(message = "分类Id不能为空")
    private Long categoryId;
    /**
     * 博客标题
     */
    @Excel(name = "标题")
    @Length(min = 3, max = 100, message = "文章标题不能为空，且长度为{min}~{max}个字符")
    private String title;
    /**
     * 内容摘要
     */
    @NotNull(message = "摘要不能为空")
    @Length(min = 10, max = 250, message = "摘要长度为{min}~{max}个字符")
    @Excel(name = "摘要")
    private String summary;
    /**
     * 封面图类型.0无,1普通,2大图
     */
    @Excel(name = "封面类型")
    private Integer headerImgType;
    /**
     * 封面路径
     */
    @Excel(name = "封面路径")
    private String headerImg;
    /**
     * 博客内容
     */
    @Excel(name = "内容")
    @NotNull(message = "正文内容不能为空")
    private String content;
    /**
     * html格式内容
     */
    @Excel(name = "html格式内容")
    private String htmlContent;

    /**
     * 博文状态，1表示已经发表，2表示在草稿箱，3表示在垃圾箱
     */
    @Excel(name = "状态" ,readConverterExp = "1=已发布,2=草稿箱,3=垃圾箱")
    @NotNull(message = "状态设置不能为空")
    private Integer status;
    /**
     * 评论标识(1:允许,0:不允许)
     */
    @Excel(name = "是否允许评论",readConverterExp = "1=允许,0=不允许")
    @NotNull(message = "评论设置不能为空")
    private Integer comment;
    /**
     * 是否推荐(置顶)，1表示推荐，0表示不推荐
     */
    @Excel(name = "是否推荐",readConverterExp = "1=推荐,0=普通")
    @NotNull(message = "推荐设置不能为空")
    private Integer support;
    /**
     * 权重
     */
    @Excel(name = "权重")
    private Integer weight;
    /**
     * 分类
     */
    private Category category;
    /**
     * 点赞数
     */
    @Excel(name = "点赞数")
    private Integer like;
    /**
     * 点击量
     */
    @Excel(name = "点击数")
    private Integer click;

    /**
     * 原创标识(1:原创,0:转载)
     */
    @Excel(name = "是否原创",readConverterExp = "1=原创,0=转载")
    private Integer original;

    /**
     * 标签集合
     */
    private List<Tag> tagList;
    /**
     * 标签名集合
     */
    private List<String> tagTitleList;
    /**
     * 评论集合
     */
    private List<Comment> commentList;
    /**
     * 评论数量
     */
    private Integer commentCount;
    /**
     * 创建者
     */
    @Excel(name = "创建人")
    private String createBy;
    /**
     * 修改者
     */
    private String updateBy;
}
