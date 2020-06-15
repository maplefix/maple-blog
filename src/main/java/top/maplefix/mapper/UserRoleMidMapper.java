package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.UserRoleMid;

import java.util.List;

/**
 * @author Maple
 * @description 用户角色中间表关系
 * @date 2020/2/2 16:45
 */
public interface UserRoleMidMapper extends Mapper<top.maplefix.model.UserRoleMid> {
    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserRoleByUserId(String userId);

    /**
     * 批量删除用户和角色关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteUserRole(String[] ids);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int countUserRoleByRoleId(String roleId);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchUserRole(List<UserRoleMid> userRoleList);

    /**
     * 删除用户和角色关联信息
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    int deleteUserRoleInfo(UserRoleMid userRole);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int deleteUserRoleInfos(@Param("roleId") String roleId, @Param("userIds") String[] userIds);
}
