package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangjg
 * @description 轮播图实体类
 * @date 2020/2/5 18:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Carousel extends BaseEntity implements Serializable {

    @NotNull(message = "主键不能为空")
    private String  id;

    @NotNull(message = "标题不能为空")
    private String title;
    /**
     * 显示文本
     */
    @Length(min = 5, max = 150, message = "描述长度为{min}~{max}")
    private String description;
    /**
     * 点击次数
     */
    private Long click;
    /**
     * 图片URL
     */
    @URL(message = "URL不合法")
    private String headerImg;
    /**
     * 是否显示
     */
    @NotNull(message = "显示配置不能为空")
    private Boolean display;
    /**
     * 是否当前窗口打开
     */
    @NotNull(message = "target配置不能为空")
    private Boolean target;

    @NotNull(message = "URL不能为空")
    private String url;

}

