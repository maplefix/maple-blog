package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Maple
 * @description 评论实体类
 * @date 2020/1/15 15:19
 */
@Data
@Table(name = "t_comment")
public class Comment implements Serializable {

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String commentId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     *Email地址
     */
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
     * 父评论id
     */
    private String parentId;
    /**
     * QQ号
     */
    private String qqNum;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 页面id
     */
    private String pageId;
    /**
     * 页面俩
     */
    private String url;
    /**
     * 是否显示，1显示，0不显示
     */
    private Integer display;
    /**
     * 点赞
     */
    private String good;
    /**
     * 踩
     */
    private String bad;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 创建时间
     */
    private String createData;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * html内容
     */
    private String htmlContent;
    /**
     * 回复
     */
    private String replay;
    /**
     * 管理员回复
     */
    private String adminReplay;
    /**
     * 回复id
     */
    private String replayId;
}
