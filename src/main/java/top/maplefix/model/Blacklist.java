package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.maplefix.annotation.Excel;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Maple
 * @description 黑名单实体类
 * @date 2020/3/18 14:48
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Blacklist extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * ip 地址
     */
    @Pattern(regexp = "^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}$", message = "IP地址不合法")
    @Excel(name = "封禁IP")
    private String ip;
    /**
     * 封禁原因描述
     */
    @Length(min = 3, max = 50, message = "封禁原因长度为{min}~{max}")
    @Excel(name = "封禁原因")
    private String description;
    /**
     * 拦截次数
     */
    @Excel(name = "拦截次数")
    private String interceptCount;
    /**
     * 上次拦截的Url
     */
    @Excel(name = "上次拦截的Url")
    private String lastAccessUrl;
    /**
     * 上次访问的地址
     */
    @Excel(name = "上次访问的地址")
    private String lastAccessDate;

}
