package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Role;

import java.util.List;
import java.util.Map;

/**
 * @author Maple
 * @description 角色查询mapper
 * @date 2020/1/17 11:13
 */
public interface RoleMapper extends Mapper<Role> {

    /**
     * 根据条件查询角色信息
     * @param queryMap 查询条件
     * @return list
     */
    List<Role> getRoleByCondition(@Param("map") Map<String,Object> queryMap);

    /**
     * 根据用户id查询角色
     * @param userId 用户id
     * @return role集合
     */
    List<Role> getRoleByUserId(@Param("userId") String userId);
}
