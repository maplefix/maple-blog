package top.maplefix.service;

import top.maplefix.model.Label;

/**
 * @author : Maple
 * @description : 博客标签关联接口
 * @date : Created in 2019/7/25 17:15
 * @version : v2.1
 */
public interface IBlogLabelService {

    /**
     * 根据标签id查询该标签是否与博客关联
     * @param labelIds
     * @return
     */
    boolean isExistBlogLabel(String[] labelIds);

    /**
     * 查询标签关联的博客数量
     * @param label
     * @return
     */
    int countBlogByLabel(Label label);

}
