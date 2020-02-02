package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Maple
 * @description 角色菜单中间表实体类
 * @date 2020/1/15 16:12
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "t_role_menu_mid")
public class RoleMenuMid implements Serializable {

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String rmmId;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 菜单id
     */
    private String menuId;
    /**
     * 创建时间
     */
    private String createDate;
}
