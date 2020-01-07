package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 访问日志实体
 * @date : Created in 2019/7/24 0:06
 * @version : v1.0
 */
@Data
@Table(name = "t_visit_log")
@NameStyle
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
    private String visitIp;
    /**
     * 访问者地区
     */
    @Excel(name = "访问者地区")
    private String visitLocation;
    /**
     * 访问者浏览器
     */
    @Excel(name = "访问者浏览器")
    private String visitBrowser;
    /**
     * 访问者操作系统
     */
    @Excel(name = "访问者操作系统")
    private String visitOs;
    /**
     * 请求地址
     */
    @Excel(name = "请求地址")
    private String requestUrl;
    /**
     * 错误信息
     */
    @Excel(name = "错误信息")
    private String errorMsg;
    /**
     * 模块名
     */
    @Excel(name = "模块名")
    private String module;
    /**
     * 访问状态(0:成功,1:失败)
     */
    @Excel(name = "访问状态",readConverterExp = "1=失败,0=成功")
    private String status;
    /**
     * 访问时间
     */
    @Excel(name = "访问时间")
    private String visitDate;
}
