package top.maplefix.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.maplefix.constant.CacheConstants;
import top.maplefix.constant.Constant;
import top.maplefix.exception.CustomException;
import top.maplefix.mapper.ConfigMapper;
import top.maplefix.model.Config;
import top.maplefix.redis.CacheExpire;
import top.maplefix.redis.TimeType;
import top.maplefix.service.ConfigService;
import top.maplefix.utils.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author : Maple
 * @description : 参数配置service实现类
 * @date : 2020/4/17 19:46
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigMapper configMapper;

    @Override
    public Config selectConfigById(String configId) {
        Config config = new Config();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    @Override
    public Config selectConfigByKey(String configKey) {
        Config config = new Config();
        config.setConfigKey(configKey);
        Config retConfig = configMapper.selectConfig(config);
        return Objects.isNull(retConfig) ? null : retConfig;
    }

    @Override
    public List<Config> selectConfigList(Config config) {
        return configMapper.selectConfigList(config);
    }

    @Override
    public int insertConfig(Config config) {
        return configMapper.insertConfig(config);
    }

    @Override
    public int updateConfig(Config config) {
        return configMapper.updateConfig(config);
    }

    @Override
    public int updateConfigByConfigKey(Config config) {
        //校验是否存在当前配置
        Config configDB = configMapper.selectConfig(config);
        if (Objects.isNull(configDB)) {
            return insertConfig(config);
        }
        return configMapper.updateConfigByConfigKey(config);
    }

    @Override
    public int deleteConfigById(String id) {
        return configMapper.deleteConfigById(id);
    }

    @Override
    public String checkConfigKeyUnique(Config config) {
        String id = StringUtils.isNull(config.getConfigId()) ? "" : config.getConfigId();
        Config info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && !info.getConfigId().equals(id)) {
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_BACKEND_CONFIG, key = "#key")
    @CacheExpire(expire = 5, type = TimeType.HOURS)
    public <T> T selectConfigByConfigKey(String key, Class<T> tClass) {
        Config config = new Config();
        config.setConfigKey(key);
        Config retConfig = configMapper.selectConfig(config);
        if (retConfig == null) {
            throw new CustomException("Can not get config by key  " + key);
        }
        return JSON.parseObject(retConfig.getConfigValue(), tClass);
    }
}
