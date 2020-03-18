package top.maplefix.secrrity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.maplefix.model.Role;
import top.maplefix.model.SysUser;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Maple
 * @description 登陆用户信息
 * @date 2020/1/14 17:25
 */
@Data
public class LoginUser implements Serializable, UserDetails {

    /**
     * 用户登陆code
     */
    private String userToken;
    /**
     * login time
     */
    private Long loginTime;

    /**
     * expire time
     */
    private Long expireTime;

    /**
     * Login IP address
     */
    private String ip;

    /**
     * location
     */
    private String location;

    /**
     * Browser type
     */
    private String browser;

    /**
     * operating system
     */
    private String os;
    /**
     * 登陆用户
     */
    private SysUser user;
    /**
     * 角色集合
     */
    private List<Role> role;
    /**
     * 权限集合
     */
    private Set<String> permissions;

    public LoginUser(SysUser user){
        this.user = user;
    }

    public LoginUser(SysUser user, Set<String> permissions){
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * Whether the account has not expired, which cannot be verified
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Specifies whether the user is unlocked. Locked users cannot authenticate
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (passwords) have expired, which prevents authentication
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Available, disabled users cannot authenticate
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
