package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 友链实体
 * @date : 2020/1/15 14：51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Link extends BaseEntity implements Serializable {

    /**
     * 友链表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String linkId;
    /**
     * 友链名称
     */
    @Excel(name = "友链名称")
    private String linkName;
    /**
     * 链接地址
     */
    @Excel(name = "链接地址")
    private String url;
    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;
    /**
     * 头像地址
     */
    @Excel(name = "头像地址")
    private String headerImg;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;
    /**
     * 是否显示(1:显示,0:不显示)
     */
    @Excel(name = "审核状态",readConverterExp = "1=通过,0=不通过")
    private Integer status;
    /**
     * 是否显示(1:显示,0:不显示)
     */
    @Excel(name = "是否显示",readConverterExp = "1=显示,0=不显示")
    private Integer display;
    /**
     * 排序值
     */
    @Excel(name = "排序值")
    private String  seq;

}
