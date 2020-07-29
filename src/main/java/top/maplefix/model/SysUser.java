package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Maple
 * @description : 用户表实体类
 *  JsonInclude 序列化Json的时候,如果是Null则忽略
 * @date : 2020/1/15 15:09
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class SysUser extends BaseEntity implements Serializable {

    public static final String ADMIN = "f0e38fd7bcee4c248a0908258d6947d7";

    public SysUser(String id) {
        this.userId = id;
    }
    /**
     * 用户表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String userId;
    /**
     * 用户名
     */
    @Excel(name = "用户名称")
    private String userName;
    /**
     * 用户名
     */
    @Excel(name = "用户昵称")
    private String nickName;
    /**
     * 邮箱
     */
    @Excel(name = "邮件地址")
    private String email;
    /**
     * 电话
     */
    @Excel(name = "电话")
    private String phone;
    /**
     * 性别
     */
    @Excel(name = "性别",readConverterExp = "0=女,1=男")
    private String gender;
    /**
     * 头像相对路径
     */
    @Excel(name = "头像地址")
    private String avatar;
    /**
     * 密码
     */
    @Excel(name = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * 盐加密
     */
    private String salt;
    /**
     * 帐号状态(0停用,1正常)
     */
    @Excel(name = "用户状态", readConverterExp = "0=停用,1=正常")
    private Integer status;
    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间")
    private String loginDate;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 角色对象
     */
    private List<Role> roles;

    /**
     * 角色组
     */
    private String[] roleIds;

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(String userId) {
        return ADMIN.equals(userId);
    }
}
