package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.maplefix.annotation.Excel;

import java.io.Serializable;

/**
 * @author : Maple
 * @description : 访问日志实体
 * @date : 2020/1/15 15:11
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitLog extends BaseEntity implements Serializable {

    /**
     * 访问日志表实体主键
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 访问者IP
     */
    @Excel(name = "访问者IP")
    private String ip;
    /**
     * 页面id
     */
    @Excel(name = "页面id")
    private String pageId;
    /**
     * 访问者地区
     */
    @Excel(name = "访问者地区")
    private String location;
    /**
     * 访问者浏览器
     */
    @Excel(name = "访问者浏览器")
    private String browser;
    /**
     * 访问者操作系统
     */
    @Excel(name = "访问者操作系统")
    private String os;
    /**
     * 请求地址
     */
    @Excel(name = "请求地址")
    private String url;
    /**
     * 错误信息
     */
    @Excel(name = "错误信息")
    private String exceptionMsg;
    /**
     * 模块名
     */
    @Excel(name = "模块名")
    private String module;

    @Excel(name = "爬虫")
    private String spider;
    /**
     * 访问状态(1:成功,0:失败)
     */
    @Excel(name = "访问状态",readConverterExp = "0=失败,1=成功")
    private Integer status;
    /**
     * 入口地址
     */
    @Excel(name = "入口地址")
    private String entryUrl;

}
