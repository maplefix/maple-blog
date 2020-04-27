package top.maplefix.service;

import top.maplefix.model.Blacklist;

import java.util.List;

/**
 * @author Maple
 * @description 黑名单service接口
 * @date 2020/3/18 15:06
 */
public interface BlacklistService {

    /**
     * 删除黑名单记录
     *
     * @param ids id
     * @return 受影响的行数
     */
    int deleteBlacklistByIds(String ids);

    /**
     * 更新黑名单
     *
     * @param blacklist 黑名单
     * @return 受影响的行数
     */
    int updateBlacklist(Blacklist blacklist);

    /**
     * 新增黑名单
     *
     * @param blacklist 黑名单对象
     * @return 受影响的行数
     */
    int insertBlacklist(Blacklist blacklist);

    /**
     * 根据id查询黑名单记录
     *
     * @param id id
     * @return 黑名单对象
     */
    Blacklist selectBlacklistById(String id);

    /**
     * 获取黑名单集合
     *
     * @param blacklist 黑名单对象
     * @return 黑名单集合
     */
    List<Blacklist> selectBlacklistList(Blacklist blacklist);
}
