package top.maplefix.mapper;

import org.springframework.data.repository.query.Param;
import top.maplefix.model.Config;

import java.util.List;

/**
 * @author Maple
 * @description 参数配置mapper
 * @date 2020/4/17 16:21
 */
public interface ConfigMapper {
    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    Config selectConfig(Config config);

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<Config> selectConfigList(Config config);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    Config checkConfigKeyUnique(String configKey);

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    int insertConfig(Config config);

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    int updateConfig(Config config);

    /**
     * 根据Config Key 进行修改
     *
     * @return 受影响的行数
     */
    int updateConfigByConfigKey(Config config);

    /**
     * 删除参数配置
     *
     * @param id    需要删除的数据ID
     * @return 结果
     */
    int deleteConfigById(@Param("id") Long  id);
}
