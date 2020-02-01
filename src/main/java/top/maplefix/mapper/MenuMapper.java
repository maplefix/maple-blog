package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Menu;

import java.util.List;

/**
 * @author Maple
 * @description 菜单权限mapper
 * @date 2020/1/17 11:13
 */
public interface MenuMapper extends Mapper<Menu> {

    /**
     * 根据角色id获取菜单信息
     * @return
     */
    List<Menu>  getMenuByRoleId(@Param("roleId") String roleId);
}
