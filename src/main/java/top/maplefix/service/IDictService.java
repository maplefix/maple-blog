package top.maplefix.service;

import top.maplefix.model.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 字典表接口
 * @date : Created in 2019/7/24 22:54
 * @version : v1.0
 */
public interface IDictService {

    /**
     * 根据关键字查询系统配置数据列表
     * @param keyWord
     * @return
     */
    List<Dict> getSystemConfig(String keyWord);

    /**
     * 插叙字典项总数
     * @return
     */
    int getTotalDict();

    /**
     * 分页查询字典列表
     * @param params
     * @return
     */
    List<Dict> getDictPage(Map<String, Object> params);

    /**
     * 查询该字典是否已存在
     * @param dict
     * @return
     */
    Dict isExistDict(Dict dict);

    /**
     * 根据id查询
     * @param dictId
     * @return
     */
    Dict selectById(String dictId);

    /**
     * 保存字典
     * @param dict
     */
    void saveDict(Dict dict);

    /**
     * 修改字典
     * @param dict
     */
    void updateDict(Dict dict);

    /**
     * 根据关键字和字典名更新字典记录
     *  @param keyWord 关键字
     *  @param dictName 字典名
     *  @param dictValue 字典值
     */
    void updateByKeywordAndDictName(String keyWord, String dictName, String dictValue);
    /**
     * 根据id批量删除
     * @param dictIds
     */
    void deleteBatch(String[] dictIds);

    /**
     * 根据id查询字典列表
     * @param ids 字典id数据
     * @return list
     */
    List<Dict> selectDictByIds(String[] ids);
}
