package top.maplefix.service;

import top.maplefix.model.Links;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 友链操作接口
 * @date : Created in 2019/7/24 22:56
 * @version : v2.1
 */
public interface ILinksService {

    /**
     * 查询以显示的友链总数
     * @return
     */
    int getTotalLinks();

    /**
     * 分页查询友链列表
     * @param params
     * @return
     */
    List<Links> getLinksPage(Map<String, Object> params);

    /**
     * 查询该友链是否已存在
     * @param links
     * @return
     */
    Links isExistLinks(Links links);

    /**
     * 根据id查询
     * @param linksId
     * @return
     */
    Links selectById(String linksId);

    /**
     * 保存友链
     * @param links
     */
    void saveLinks(Links links);

    /**
     * 修改友链
     * @param links
     */
    void updateLinks(Links links);

    /**
     * 根据id批量删除
     * @param linksIds
     */
    void deleteBatch(String[] linksIds);

    /**
     * 根据id查友联列表
     * @param ids 友链id数据
     * @return list
     */
    List<Links> selectLinksByIds(String[] ids);
}
