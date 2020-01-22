package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 访问日志实体
 * @date : Created in 2020/1/15 15:11
 */
@Data
@Table(name = "t_visit_log")
public class VisitLog implements Serializable {

    /**
     * 访问日志表实体
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String visitLogId;
    /**
     * 访问者IP
     */
    @Excel(name = "访问者IP")
    private String ip;
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
    private String requestUrl;
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
     * 访问时间
     */
    @Excel(name = "访问时间")
    private String visitDate;
}
