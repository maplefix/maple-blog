package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.BlacklistMapper;
import top.maplefix.model.Blacklist;
import top.maplefix.service.BlacklistService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.SecurityUtils;

import java.util.List;

/**
 * @author Maple
 * @description 黑名单接口实现类
 * @date 2020/3/18 15:06
 */
@Service
public class BlacklistServiceImpl implements BlacklistService {

    @Autowired
    private BlacklistMapper blacklistMapper;

    @Override
    public int deleteBlacklistByIds(String ids) {
        return blacklistMapper.deleteBlacklistByIds(ConvertUtils.toStrArray(ids), SecurityUtils.getUsername());
    }

    @Override
    public int updateBlacklist(Blacklist blacklist) {
        return blacklistMapper.updateBlacklist(blacklist);
    }

    @Override
    public int insertBlacklist(Blacklist blacklist) {
        return blacklistMapper.insertBlacklist(blacklist);
    }

    @Override
    public Blacklist selectBlacklistById(String id) {
        return blacklistMapper.selectBlacklistById(id);
    }

    @Override
    public List<Blacklist> selectBlacklistList(Blacklist blacklist) {
        return blacklistMapper.selectBlacklistList(blacklist);
    }
}
