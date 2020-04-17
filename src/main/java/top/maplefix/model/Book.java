package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author : Maple
 * @description: 书单表
 * @date : 2020/1/15 14:36
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String bookId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;
    /**
     * 简介
     */
    @Excel(name = "简介")
    private String summary;
    /**
     * 封面
     */
    @Excel(name = "封面")
    private String coverUrl;

    /**
     * 作者
     */
    @Excel(name = "作者")
    private String author;
    /**
     * 分类id
     */
    @Excel(name = "分类id")
    private String categoryId;
    /**
     * 出版社
     */
    @Excel(name = "出版社")
    private String publisher;
    /**
     * 出版社
     */
    @Excel(name = "出版社")
    private String publishDate;
    /**
     * 页数
     */
    @Excel(name = "页数")
    private String pageNum;
    /**
     * 评分
     */
    @Excel(name = "评分")
    private String grade;

    /**
     * 原书目录
     */
    @Excel(name = "原书目录")
    private String catalog;
    /**
     * 阅读量
     */
    @Excel(name = "阅读量")
    private Integer click;
    /**
     * 喜欢
     */
    @Excel(name = "喜欢")
    private Integer like;
    /**
     * 状态，1表示发布,0表示草稿箱
     */
    @Excel(name = "喜欢")
    private Integer status;

    /**
     * 是否推荐
     */
    @Excel(name = "是否推荐",readConverterExp = "0=不推荐,1=推荐")
    private Integer support;
    /**
     * 阅读结束时间
     */
    @Excel(name = "阅读状态",readConverterExp = "0=未开始,1=阅读中,2=已结束")
    private Integer progress;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 更新时间
     */
    private String updateDate;

}
