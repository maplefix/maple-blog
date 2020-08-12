package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.maplefix.annotation.Excel;

import java.io.Serializable;

/**
 * @author : Maple
 * @description : 登录日志实体
 * @date : 2020/1/15 15：09
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginLog extends BaseEntity implements Serializable {

    /**
     * 主键id
     */
    @Excel(name = "编号")
    private Long id;
    /**
     * 登录名
     */
    @Excel(name = "登录名")
    private String userName;
    /**
     * 登录IP
     */
    @Excel(name = "登录IP")
    private String ip;
    /**
     * 登录地区
     */
    @Excel(name = "登录地区")
    private String location;
    /**
     * 登录浏览器
     */
    @Excel(name = "登录浏览器")
    private String browser;
    /**
     * 登录操作系统
     */
    @Excel(name = "登录操作系统")
    private String os;
    /**
     * 登录状态（1:成功,0:失败）
     */
    @Excel(name = "登录状态",readConverterExp = "0=失败,1=成功")
    private Integer status;
    /**
     * 登录描述
     */
    @Excel(name = "登录描述")
    private String loginMsg;
}
