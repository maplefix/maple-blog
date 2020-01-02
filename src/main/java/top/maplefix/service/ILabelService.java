package top.maplefix.service;

import top.maplefix.model.Label;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客标签接口
 * @date : Created in 2019/7/24 22:55
 * @version : v2.1
 */
public interface ILabelService {

    /**
     * 查询首页
     * @return
     */
    List<Label> getBlogTagForIndex();

    /**
     * 获取标签总数
     * @return
     */
    int getTotalLabel();

    /**
     * 分页查询标签数据
     * @param params
     * @return
     */
    List<Label> getLabelPage(Map<String, Object> params);

    /**
     * 查询标签是否已存在
     * @param label
     * @return
     */
    Label isExistLabel(Label label);

    /**
     * 保存标签
     * @param label
     */
    void saveLabel(Label label);

    /**
     * 根据id批量删除
     * @param labelIds
     */
    void deleteBatch(String[] labelIds );

    /**
     * 编辑标签
     * @param label
     */
    void updateLabel(Label label);

    /**
     * 根据id查询标签列表
     * @param ids 标签id数据
     * @return list
     */
    List<Label> selectLabelByIds(String[] ids);
}
