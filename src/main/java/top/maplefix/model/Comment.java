package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.maplefix.annotation.Excel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Maple
 * @description 评论实体类
 * @date 2020/1/15 15:19
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空")
    @Length(min = 1, max = 100, message = "昵称长度为{min}~{max}个字符")
    private String nickName;
    /**
     *Email地址
     */
    @Email(message = "Email地址不合法")
    private String email;
    /**
     *IP地址
     */
    private String ip;
    /**
     * 地理位置
     */
    private String location;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 父评论的id
     */
    private Long parentId;
    /**
     * QQ号
     */
    @Length(max = 11, message = "QQ号码长度不能超过{max}")
    private String qqNum;
    /**
     * 头像地址
     */
    @Length(max = 256, message = "头像地址长度不能超过{max}")
    private String avatar;
    /**
     * 页面id
     */
    private Long pageId;
    /**
     * 页面url
     */
    private String url;
    /**
     * 是否显示，1显示，0不显示
     */
    private Integer display;
    /**
     * 点赞
     */
    private Long good;
    /**
     * 踩
     */
    private Long bad;
    /**
     * 评论内容
     */
    @NotNull(message = "内容不能为空")
    private String content;
    /**
     * html内容
     */
    @NotNull(message = "内容不能为空")
    private String htmlContent;
    /**
     * 是否接收回复邮件，1是，0否
     */
    private String reply;
    /**
     * 回复的id
     */
    private String replyId;
    /**
     * 是否管理员回复，1是，0否
     */
    private Integer adminReply;

    private Comment parentComment;
    /**
     * 子评论
     */
    private List<Comment> subCommentList;
    /**
     * 回复的NickName
     */
    private String replyNickName;
}
