package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.maplefix.constant.Constant;
import top.maplefix.mapper.DictDataMapper;
import top.maplefix.mapper.DictTypeMapper;
import top.maplefix.model.DictType;
import top.maplefix.service.DictTypeService;
import top.maplefix.utils.StringUtils;

import java.util.List;

/**
 * @author Maple
 * @description 字典类型实体类
 * @date 2020/4/17 9:36
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {
    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Autowired
    private DictDataMapper dictDataMapper;

    @Override
    public List<DictType> selectDictTypeList(DictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    @Override
    public List<DictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    @Override
    public DictType selectDictTypeById(String dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    @Override
    public DictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    @Override
    public int deleteDictTypeById(String dictId) {
        return dictTypeMapper.deleteDictTypeById(dictId);
    }

    @Override
    public int insertDictType(DictType dictType) {
        return dictTypeMapper.insertDictType(dictType);
    }

    @Override
    @Transactional
    public int updateDictType(DictType dictType) {
        DictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDataTypeId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        return dictTypeMapper.updateDictType(dictType);
    }

    @Override
    public String checkDictTypeUnique(DictType dict) {
        String dictId = StringUtils.isNull(dict.getDataTypeId()) ? "" : dict.getDataTypeId();
        DictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (StringUtils.isNotNull(dictType) && !dictType.getDataTypeId().equals(dictId)) {
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }
}
