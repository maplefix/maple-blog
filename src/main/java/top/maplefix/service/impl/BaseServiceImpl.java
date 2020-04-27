package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

/**
 * @author : Maple
 * @description : service基类
 * @date : 2019/12/24 22:41
 */
public abstract class BaseServiceImpl<T, R extends Mapper<T>>{

    @Autowired
    R mapper;


    /**
     * 根据实体对象查询一条记录
     * @param t
     * @return
     */
    public T getOne(T t){
        return mapper.selectOne(t);
    }

    /**
     * 根据实体对象插入一条记录
     * @param t
     */
    public void addOne(T t){
        mapper.insert(t);
    }

    /**
     * 根据一个字段名和字段值查询该对象是否存在
     * @param c
     * @param column
     * @param value
     * @return
     */
    public boolean isExist(Class c,String column,String value){
        Example example = new Example(c);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(column,value);
        int count = mapper.selectCountByExample(example);
        if(count >=1 ){
            return true;
        }else {
            return false;
        }
    }
}
