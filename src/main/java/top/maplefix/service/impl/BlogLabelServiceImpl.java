package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.mapper.BlogLabelMapper;
import top.maplefix.model.BlogLabel;
import top.maplefix.model.Label;
import top.maplefix.service.IBlogLabelService;

import java.util.List;

/**
 * @author : Maple
 * @description :
 * @date : Created in 2019/7/25 17:15
 * @editor:
 * @version: v2.1
 */
@Service
public class BlogLabelServiceImpl implements IBlogLabelService {

    @Autowired
    private BlogLabelMapper blogLabelMapper;


    @Override
    public boolean isExistBlogLabel(String[] labelIds) {
        for (String labelId : labelIds){
            Example example = new Example(BlogLabel.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("labelId", labelId);
            List<BlogLabel> labelList = blogLabelMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(labelList)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int countBlogByLabel(Label label) {
        Example example = new Example(BlogLabel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("labelId", label.getLabelId());
        blogLabelMapper.selectCountByExample(example);
        return blogLabelMapper.selectCountByExample(example);
    }
}
