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
 * @description : 用户表实体
 * @date : Created in 2019/7/24 0:05
 * @editor:
 * @version: v2.1
 */
@Data
@Table(name = "t_user")
@NameStyle
public class User implements Serializable {

    /**
     * 用户表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String userId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 性别
     */
    private String gender;
    /**
     * 头像相对路径
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 帐号状态(0正常 1停用)
     */
    private String status;
    /**
     * 删除标识(1:删除,0:正常)
     */
    private String delFlag;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private String loginDate;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 备注
     */
    private String remark;


}
