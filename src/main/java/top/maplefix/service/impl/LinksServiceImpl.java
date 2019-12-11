package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.Constant;
import top.maplefix.constant.LinksConstant;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.LinksMapper;
import top.maplefix.model.Links;
import top.maplefix.service.ILinksService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.UuidUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description :
 * @date : Created in 2019/7/24 22:56
 * @editor:
 * @version: v2.1
 */
@Service
public class LinksServiceImpl implements ILinksService {

    @Autowired
    private LinksMapper linksMapper;

    @Override
    public int getTotalLinks() {
        Example example = new Example(Links.class);
        Example.Criteria criteria = example.createCriteria();
        //标签状态为未删除且显示
        criteria.andEqualTo("display", LinksConstant.DISPLAY);
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        return linksMapper.selectCountByExample(example);
    }
    @Override
    public List<Links> getLinksPage(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        String linksName = top.maplefix.utils.StringUtils.getObjStr(params.get("linksName"));
        String linksType = top.maplefix.utils.StringUtils.getObjStr(params.get("linksType"));
        Example example = new Example(Links.class);
        //根据时间排序
        example.setOrderByClause("createDate desc");
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(linksName)){
            criteria.andEqualTo("linksName", linksName);
        }
        if(!StringUtils.isEmpty(linksType)){
            criteria.andEqualTo("linksType", linksType);
        }
        //未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        PageHelper.startPage(currPage, pageSize);
        return linksMapper.selectByExample(example);
    }

    @Override
    public Links isExistLinks(Links links) {
        Example example = new Example(Links.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("linksName", links.getLinksName());
        //未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        return linksMapper.selectOneByExample(example);
    }

    @Override
    public Links selectById(String linksId) {
        Links links = new Links();
        links.setLinksId(linksId);
        return linksMapper.selectByPrimaryKey(links);
    }

    @Override
    public void saveLinks(Links links) {
        links.setLinksId(UuidUtils.getRandomUuidWithoutSeparator());
        links.setDelFlag(Constant.NORMAL);
        links.setCreateDate(DateUtils.getCurrDate());
        linksMapper.insert(links);
    }

    @Override
    public void updateLinks(Links links) {
        links.setUpdateDate(DateUtils.getCurrDate());
        linksMapper.updateByPrimaryKeySelective(links);
    }

    @Override
    public void deleteBatch(String[] linksIds) {
        for (String linkId : linksIds){
            Links links = new Links();
            links.setLinksId(linkId);
            links.setDelFlag(Constant.DELETED);
            linksMapper.updateByPrimaryKeySelective(links);
        }
    }

}
