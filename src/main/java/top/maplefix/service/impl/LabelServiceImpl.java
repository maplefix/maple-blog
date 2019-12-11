package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.Constant;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.BlogLabelMapper;
import top.maplefix.mapper.LabelMapper;
import top.maplefix.model.BlogLabel;
import top.maplefix.model.Label;
import top.maplefix.service.ILabelService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.UuidUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 博客标签接口实现类
 * @date : Created in 2019/7/24 22:55
 * @editor:
 * @version: v2.1
 */
@Service
public class LabelServiceImpl implements ILabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private BlogLabelMapper blogLabelMapper;

    @Override
    public List<Label> getBlogTagForIndex() {
        Example example = new Example(Label.class);
        Example.Criteria criteria = example.createCriteria();
        //标签状态为未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);

        List<Label> labelList = labelMapper.selectByExample(example);
        BlogLabel blogLabel = new BlogLabel();
        for(Label temp: labelList){
            blogLabel.setLabelId(temp.getLabelId());
            int count = blogLabelMapper.selectCount(blogLabel);
            temp.setCount(count);
        }
        return labelList;
    }

    @Override
    public int getTotalLabel() {

        Example example = new Example(Label.class);
        Example.Criteria criteria = example.createCriteria();
        //标签状态未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);

        return labelMapper.selectCountByExample(example);
    }

    @Override
    public List<Label> getLabelPage(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        String labelName = top.maplefix.utils.StringUtils.getObjStr(params.get("labelName"));
        String startDate = top.maplefix.utils.StringUtils.getObjStr(params.get("startDate"));
        String endDate = top.maplefix.utils.StringUtils.getObjStr(params.get("endDate"));
        PageHelper.startPage(currPage, pageSize);
        Example example = new Example(Label.class);
        //根据时间排序
        example.setOrderByClause("createDate desc");
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(labelName)){
            criteria.andEqualTo("labelName", labelName);
        }
        if(!StringUtils.isEmpty(startDate)){
            criteria.andGreaterThan("createDate", startDate + " 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andLessThan("createDate", endDate + " 23:59:59");
        }
        //未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        List<Label> labelList = labelMapper.selectByExample(example);
        BlogLabel blogLabel = new BlogLabel();
        for(Label label: labelList){
            blogLabel.setLabelId(label.getLabelId());
            int count = blogLabelMapper.selectCount(blogLabel);
            label.setBlogCount(count);
        }

        return labelList;
    }

    @Override
    public Label isExistLabel(Label label) {
        Example example = new Example(Label.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("labelName", label.getLabelName());
        //未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        return labelMapper.selectOneByExample(example);

    }

    @Override
    public void saveLabel(Label label) {
        label.setLabelId(UuidUtils.getRandomUuidWithoutSeparator());
        label.setDelFlag(Constant.NORMAL);
        label.setCreateDate(DateUtils.getCurrDate());
        labelMapper.insert(label);
    }

    @Override
    public void deleteBatch(String[] labelIds) {
        for (String labelId : labelIds){
            Label label = new Label();
            label.setLabelId(labelId);
            label.setDelFlag(Constant.DELETED);
            labelMapper.updateByPrimaryKeySelective(label);
        }
    }

    @Override
    public void updateLabel(Label label) {
        label.setUpdateDate(DateUtils.getCurrDate());
        labelMapper.updateByPrimaryKeySelective(label);
    }
}
