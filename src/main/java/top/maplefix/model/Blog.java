package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.maplefix.annotation.Excel;

import javax.persistence.Transient;
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
     * 博客标题
     */
    @Excel(name = "标题")
    @Length(min = 3, max = 100, message = "文章标题不能为空，且长度为{min}~{max}个字符")
    private String title;
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
     * tml格式内容
     */
    @Excel(name = "html格式内容")
    @NotNull(message = "html正文内容不能为空")
    private String htmlContent;
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
    @Excel(name = "分类名称")
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
    @NotNull(message = "状态设置不能为空")
    private Integer status;
    /**
     * 点击量
     */
    @Excel(name = "点击量")
    private Integer click;
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
     * 是否推荐(置顶)，1表示推荐，0表示不推荐
     */
    @Excel(name = "是否推荐",readConverterExp = "1=推荐,0=普通")
    @NotNull(message = "推荐设置不能为空")
    private Integer isRecommend;
    /**
     * 原创标识(1:原创,0:转载)
     */
    @Excel(name = "是否原创",readConverterExp = "1=原创,0=转载")
    private Integer isOriginal;
    /**
     * 评论标识(1:允许,0:不允许)
     */
    @Excel(name = "是否允许评论",readConverterExp = "1=允许,0=不允许")
    private Integer isComment;
    /**
     * 点赞数
     */
    private Integer like;
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
