package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.DictDataMapper;
import top.maplefix.model.DictData;
import top.maplefix.service.DictDataService;

import java.util.List;

/**
 * @author Maple
 * @description 字典数据service接口实现类
 * @date 2020/4/17 9:36
 */
@Service
public class DictDataServiceImpl implements DictDataService {
    @Autowired
    private DictDataMapper dictDataMapper;

    @Override
    public List<DictData> selectDictDataList(DictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }


    @Override
    public List<DictData> selectDictDataByType(String dictType) {
        return dictDataMapper.selectDictDataByType(dictType);
    }


    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    @Override
    public DictData selectDictDataById(String dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    @Override
    public int deleteDictDataById(String dictCode) {
        return dictDataMapper.deleteDictDataById(dictCode);
    }

    @Override
    public int insertDictData(DictData dictData) {
        return dictDataMapper.insertDictData(dictData);
    }

    @Override
    public int updateDictData(DictData dictData) {
        return dictDataMapper.updateDictData(dictData);
    }
}
