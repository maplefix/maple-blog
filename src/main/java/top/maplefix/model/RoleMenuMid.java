package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Maple
 * @description 角色菜单中间表实体类
 * @date 2020/1/15 16:12
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleMenuMid extends BaseEntity implements Serializable {

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 菜单id
     */
    private Long menuId;
}
