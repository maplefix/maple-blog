package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 友链实体
 * @date : 2020/1/15 14：51
 */
@Data
@NoArgsConstructor
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
    @Length(min = 3, max = 50, message = "名称长度为{min}~{max}")
    private String linkName;
    /**
     * 链接地址
     */
    @Excel(name = "链接地址")
    @URL(message = "请输入正确的Url地址")
    private String url;
    /**
     * 描述
     */
    @Excel(name = "描述")
    @Length(min = 3, max = 500, message = "友链描述长度为{min}~{max}")
    private String description;
    /**
     * 网站图片
     */
    @Excel(name = "网站图片")
    @URL(message = "请输入正确的网站图片地址")
    private String headerImg;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    @Email(message = "请输入正确的站长邮箱地址")
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
