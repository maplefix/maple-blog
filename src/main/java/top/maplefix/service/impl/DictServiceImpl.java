package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.Constant;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.DictMapper;
import top.maplefix.model.Dict;
import top.maplefix.service.IDictService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.UuidUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 字典表接口实现类
 * @date : Created in 2019/7/24 22:54
 * @version : v1.0
 */
@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> getSystemConfig(String keyWord) {

        Example example = new Example(Dict.class);
        Example.Criteria criteria = example.createCriteria();
        //根据关键字查询出未被删除的数据项
        if(!StringUtils.isEmpty(keyWord)){
            criteria.andEqualTo("keyWord", keyWord);
        }
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        return dictMapper.selectByExample(example);
    }

    @Override
    public int getTotalDict() {
        Example example = new Example(Dict.class);
        Example.Criteria criteria = example.createCriteria();
        //未被删除的数据项
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        return dictMapper.selectCountByExample(example);
    }

    @Override
    public List<Dict> getDictPage(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        String keyWord = top.maplefix.utils.StringUtils.getObjStr(params.get("keyWord"));
        String dictName = top.maplefix.utils.StringUtils.getObjStr(params.get("dictName"));
        Example example = new Example(Dict.class);
        //根据时间排序
        example.setOrderByClause("createDate desc");
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(keyWord)){
            criteria.andEqualTo("keyWord", keyWord);
        }
        if(!StringUtils.isEmpty(dictName)){
            criteria.andEqualTo("dictName", dictName);
        }
        //未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        PageHelper.startPage(currPage, pageSize);
        return dictMapper.selectByExample(example);
    }

    @Override
    public Dict isExistDict(Dict dict) {
        Example example = new Example(Dict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("keyWord", dict.getKeyWord());
        //未删除
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        return dictMapper.selectOneByExample(example);
    }

    @Override
    public Dict selectById(String dictId) {
        Dict dict = new Dict();
        dict.setDictId(dictId);
        return dictMapper.selectByPrimaryKey(dict);
    }

    @Override
    public void saveDict(Dict dict) {
        dict.setDictId(UuidUtils.getRandomUuidWithoutSeparator());
        dict.setDelFlag(Constant.NORMAL);
        dict.setCreateDate(DateUtils.getCurrDate());
        dictMapper.insert(dict);
    }

    @Override
    public void updateDict(Dict dict) {
        dict.setUpdateDate(DateUtils.getCurrDate());
        dictMapper.updateByPrimaryKeySelective(dict);
    }

    @Override
    public void updateByKeywordAndDictName(String keyWord, String dictName, String dictValue) {
        Example example = new Example(Dict.class);
        Dict dict = new Dict();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("keyWord", keyWord);
        criteria.andEqualTo("dictName", dictName);
        Dict one = dictMapper.selectOneByExample(example);
        if(one != null ){
            dict.setDictValue(dictValue);
            dictMapper.updateByExampleSelective(dict,example);
        }
    }

    @Override
    public void deleteBatch(String[] dictIds) {
        for (String dictId : dictIds){
            Dict dict = new Dict();
            dict.setDictId(dictId);
            dict.setDelFlag(Constant.DELETED);
            dictMapper.updateByPrimaryKeySelective(dict);
        }
    }

    @Override
    public List<Dict> selectDictByIds(String[] ids) {
        //如果前端没选中列表数据则全部导出
        if(null == ids || ids.length == 0){
            Example example = new Example(Dict.class);
            example.setOrderByClause("createDate desc");
            Example.Criteria criteria = example.createCriteria();
            //未删除
            criteria.andEqualTo("delFlag", Constant.NORMAL);
            return dictMapper.selectByExample(example);
        }
        //将数组转成字符串，用逗号隔开
        String idsStr = StringUtils.join(ids,",");
        return dictMapper.selectByIds(idsStr);
    }
}
