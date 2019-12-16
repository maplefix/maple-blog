package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: wangjg on 2019/12/2 10:48
 * @description: 书单表
 * @editored:
 * @version v 2.0
 */
@Data
@Table(name = "t_book_list")
@NameStyle(value = Style.normal)
public class BookList implements Serializable {

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String bookListId;

    /**
     * 书名
     */
    @Excel(name = "书名")
    private String bookName;

    /**
     * 作者
     */
    @Excel(name = "作者")
    private String bookAuthor;

    /**
     * 阅读开始时间
     */
    @Excel(name = "阅读开始时间")
    private String readBeginDate;

    /**
     * 阅读结束时间
     */
    @Excel(name = "阅读结束时间")
    private String readEndDate;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 更新时间
     */
    private String updateDate;

    /**
     * 书评
     */
    @Excel(name = "书评")
    private String reviews;

    /**
     * 阅读状态，0:未开始，1:阅读中,2:已结束
     */
    @Excel(name = "阅读状态" ,readConverterExp = "0=未开始,1=阅读中,2=已结束")
    private String readStatus;

    /**
     *备注说明
     */
    private String remark;

}
