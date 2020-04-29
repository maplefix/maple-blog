package top.maplefix.service;

import top.maplefix.model.Tag;

/**
 * @author : Maple
 * @description : 博客标签关联接口
 * @date : 2019/7/25 17:15
 * @version : v1.0
 */
public interface BlogLabelService {

    /**
     * 根据标签id查询该标签是否与博客关联
     * @param labelIds
     * @return
     */
    boolean isExistBlogLabel(String[] labelIds);

    /**
     * 查询标签关联的博客数量
     * @param tag
     * @return
     */
    int countBlogByLabel(Tag tag);

}
