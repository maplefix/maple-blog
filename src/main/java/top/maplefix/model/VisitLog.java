package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 访问日志实体
 * @date : Created in 2019/7/24 0:06
 * @editor:
 * @version: v2.1
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
    private String visitLogId;
    /**
     * 访问者IP
     */
    private String visitIp;
    /**
     * 访问者地区
     */
    private String visitLocation;
    /**
     * 访问者浏览器
     */
    private String visitBrowser;
    /**
     * 访问者操作系统
     */
    private String visitOs;
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 模块名
     */
    private String module;
    /**
     * 访问状态(0:成功,1:失败)
     */
    private String status;
    /**
     * 访问时间
     */
    private String visitDate;
}
