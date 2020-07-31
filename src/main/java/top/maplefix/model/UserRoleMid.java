package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Maple
 * @description 用户角色中间表实体类
 * @date 2020/1/15 16:17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleMid extends BaseEntity implements Serializable {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;

}
