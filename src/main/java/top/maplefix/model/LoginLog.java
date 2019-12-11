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
 * @description : 登录日志实体
 * @date : Created in 2019/7/24 0:05
 * @editor:
 * @version: v2.1
 */
@Data
@Table(name = "t_login_log")
@NameStyle
public class LoginLog implements Serializable {

    /**
     * 主键id
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String loginLogId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 登录地区
     */
    private String loginLocation;
    /**
     * 登录浏览器
     */
    private String loginBrowser;
    /**
     * 登录操作系统
     */
    private String loginOs;
    /**
     * 登录状态（0:成功,1:失败）
     */
    private String status;
    /**
     * 登录描述
     */
    private String loginMsg;
    /**
     * 登录日期
     */
    private String loginDate;
}
