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
 * @description 用户角色中间表实体类
 * @date 2020/1/15 16:17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "t_user_role_mid")
public class UserRoleMid implements Serializable {
    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String urmId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 创建时间
     */
    private String createData;
}
