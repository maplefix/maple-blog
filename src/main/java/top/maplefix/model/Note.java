package top.maplefix.model;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangjg
 * @description 读书笔记实体类
 * @date 2020/4/17 10:29
 */
@Data
public class Note extends BaseEntity implements Serializable {

    @Id
    private String noteId;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String summary;
    /**
     * 内容
     */
    private String content;
    /**
     * HTML 格式化后的内容
     */
    private String htmlContent;
    /**
     * 点击量
     */
    private Integer click;
    /**
     * 点赞
     */
    private Integer like;

    /**
     * 所属章节
     */
    private String chapter;
    /**
     * 图书Id
     */
    private Long bookId;
    /**
     * 封面
     */
    private String coverUrl;

    /**
     * 推荐，1推荐，0不推荐
     */
    private Integer support;
    /**
     * 评论,1允许评论，0不允许
     */
    private Integer comment;
    /**
     * 1表示发表,0表示草稿
     */
    private Integer status;

    private List<String> tagTitleList;
}
