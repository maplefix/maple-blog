package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 操作日志实体类
 * @date : 2020/1/15 15:09
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "t_operation_log")
public class OperateLog implements Serializable {

    /**
     * 操作日志表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String operateLogId;
    /**
     * 操作用户id
     */
    private String userId;
    /**
     * 模块名
     */
    @Excel(name = "模块名")
    private String module;
    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @Excel(name = "业务类型")
    private String function;
    /**
     * 方法名
     */
    @Excel(name = "方法名")
    private String method;
    /**
     * 操作ip
     */
    @Excel(name = "操作ip")
    private String ip;
    /**
     * 操作地区
     */
    @Excel(name = "操作地区")
    private String location;
    /**
     * 操作浏览器
     */
    @Excel(name = "操作浏览器")
    private String browser;
    /**
     * 操作系统
     */
    @Excel(name = "操作系统")
    private String os;

    /**
     * 操作地址
     */
    @Excel(name = "操作地址")
    private String url;

    /**
     * 请求参数
     */
    @Excel(name = "请求参数")
    private String params;
    /**
     * 请求状态(1:成功,0:失败)
     */
    @Excel(name = "请求状态",readConverterExp = "0=失败,1=成功")
    private Integer status;
    /**
     * 错误信息
     */
    @Excel(name = "错误信息")
    private String exceptionMsg;
    /**
     * 操作日期
     */
    @Excel(name = "操作日期")
    private String operationDate;

    /**
     * 耗时
     */
    @Excel(name = "耗时")
    private long cost;
}
