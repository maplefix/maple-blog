package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.constant.Constant;
import top.maplefix.exception.CustomException;
import top.maplefix.mapper.LinkMapper;
import top.maplefix.model.Link;
import top.maplefix.service.LinkService;
import top.maplefix.utils.ConvertUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author : Maple
 * @description : 友链service接口实现类
 * @date : 2020/1/24 22:56
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public Link selectLinkById(Long id) {
        return linkMapper.selectLinkById(id);
    }

    @Override
    public List<Link> selectLinkList(Link link) {
        return linkMapper.selectLinkList(link);
    }

    @Override
    public int insertLink(Link link) {
        return linkMapper.insertLink(link);
    }

    @Override
    public int updateLink(Link link) {
        return linkMapper.updateLink(link);
    }

    @Override
    public int deleteLinkByIds(String ids) {
        return linkMapper.deleteLinkByIds(ConvertUtils.toLongArray(ids));
    }

    @Override
    public int deleteLinkById(Long id) {
        return linkMapper.deleteLinkById(id);
    }

    @Override
    public int handleLinkPass(Long id, Boolean pass) {
        Link link = selectLinkById(id);
        if (Objects.isNull(link)) {
            throw new CustomException("友链不存在");
        }
        if (!pass) {
            //todo 发送email
            return linkMapper.deleteLinkById(id);
        }
        link.setStatus(Constant.DISPLAY);
        return linkMapper.updateLink(link);
    }
}
