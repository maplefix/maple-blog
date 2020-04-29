package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.mapper.BlogLabelMapper;
import top.maplefix.model.BlogTagMid;
import top.maplefix.model.Tag;
import top.maplefix.service.BlogLabelService;

import java.util.List;

/**
 * @author : Maple
 * @description :
 * @date : 2019/7/25 17:15
 * @version : v1.0
 */
@Service
public class BlogLabelServiceImpl implements BlogLabelService {

    @Autowired
    private BlogLabelMapper blogLabelMapper;


    @Override
    public boolean isExistBlogLabel(String[] labelIds) {
        for (String labelId : labelIds){
            Example example = new Example(BlogTagMid.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("labelId", labelId);
            List<BlogTagMid> labelList = blogLabelMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(labelList)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int countBlogByLabel(Tag tag) {
        Example example = new Example(BlogTagMid.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("labelId", tag.getTagId());
        blogLabelMapper.selectCountByExample(example);
        return blogLabelMapper.selectCountByExample(example);
    }
}
