package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.maplefix.annotation.Excel;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Maple
 * @description : 用户表实体类
 *  JsonInclude 序列化Json的时候,如果是Null则忽略
 * @date : 2020/1/15 15:09
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUser extends BaseEntity implements Serializable {


    public SysUser(Long id) {
        this.id = id;
    }
    /**
     * 用户表主键
     */
    @Excel(name = "主键")
    private Long id;
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
     * 性别 0男 1女 2未知
     */
    @Excel(name = "性别",readConverterExp = "1=女,0=男,2=未知")
    private String sex;
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
    private String status;
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
    private Long[] roleIds;

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    private static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
