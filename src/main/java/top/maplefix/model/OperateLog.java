package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.maplefix.annotation.Excel;

import java.io.Serializable;

/**
 * @author : Maple
 * @description : 操作日志实体类
 * @date : 2020/1/15 15:09
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperateLog extends BaseEntity implements Serializable {

    /**
     * 操作日志表主键
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 操作用户
     */
    @Excel(name = "操作用户")
    private String operateName;
    /**
     * 模块名
     */
    @Excel(name = "模块名")
    private String module;
    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @Excel(name = "业务类型")
    private String businessType;
    /**
     * 业务类型数组
     */
    private Integer[] businessTypes;
    /**
     * 方法名
     */
    @Excel(name = "方法名")
    private String method;
    /**
     * 请求方式
     */
    @Excel(name = "请求方式")
    private String requestMethod;
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
     * 请求参数
     */
    @Excel(name = "请求参数")
    private String param;
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
     * 请求返回值
     */
    @Excel(name = "请求返回值")
    private String result;

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
     * 耗时
     */
    @Excel(name = "耗时")
    private long cost;
}
