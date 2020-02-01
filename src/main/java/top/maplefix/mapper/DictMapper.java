package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.data.repository.query.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Dict;

import java.util.List;

/**
 * @author : Maple
 * @description : 字典mapper
 * @date : 2020/1/22 20:04
 */
@CacheNamespace
public interface DictMapper extends Mapper<Dict>{

    /**
     * 查询参数配置信息
     *
     * @param dict 参数配置信息
     * @return 参数配置信息
     */
    Dict selectDict(Dict dict);

    /**
     * 查询参数配置列表
     *
     * @param dict 参数配置信息
     * @return 参数配置集合
     */
    List<Dict> selectDictList(Dict dict);

    /**
     * 根据键名查询参数配置信息
     *
     * @param dictKey 参数键名
     * @return 参数配置信息
     */
    Dict checkDictKeyUnique(String dictKey);

    /**
     * 新增参数配置
     *
     * @param dict 参数配置信息
     * @return 结果
     */
    int insertDict(Dict dict);

    /**
     * 修改参数配置
     *
     * @param dict 参数配置信息
     * @return 结果
     */
    int updateDict(Dict dict);

    /**
     * 根据Dict Key 进行修改
     * @param dict dict
     * @return 受影响的行数
     */
    int updateDictByDictKey(Dict dict);

    /**
     * 删除参数配置
     *
     * @param dictId            需要删除的数据ID
     * @return 结果
     */
    int deleteDictById(@Param("dictId") String dictId);
}
