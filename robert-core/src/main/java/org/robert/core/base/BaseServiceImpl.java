package org.robert.core.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    public MyMapper<T> mapper;


    @Override
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int deleteById(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int deleteByIds(String s) {
        return mapper.deleteByIds(s);
    }

    @Override
    public int deleteByIds(List<Object> ids) {
        if (ids != null && (!ids.isEmpty())) {
            return mapper.deleteByIds(StringUtils.join(ids, ","));
        }
        return 0;
    }

    @Override
    public int deleteByIds(Object[] ids) {
        if (ids != null && ids.length > 0) {
            return mapper.deleteByIds(StringUtils.join(ids, ","));
        }
        return 0;
    }

    @Override
    public int delete(T record) {
        return mapper.delete(record);
    }

    @Override
    public int deleteByCondition(Object condition) {
        return mapper.deleteByCondition(condition);
    }

    @Override
    public int deleteByExample(Object example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public T selectOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    @Override
    public List<T> selectByExample(Object o) {
        return mapper.selectByExample(o);
    }

    @Override
    public List<T> selectByCondition(Object o) {
        return mapper.selectByCondition(o);
    }

    @Override
    public List<T> selectByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public T selectById(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public boolean existsWithPrimaryKey(Object key) {
        return mapper.existsWithPrimaryKey(key);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public int selectCountByCondition(Object o) {
        return mapper.selectCountByCondition(o);
    }

    @Override
    public int selectCountByExample(Object o) {
        return mapper.selectCountByExample(o);
    }

    @Override
    public T selectOneByExample(Object o) {
        return mapper.selectOneByExample(o);
    }

    @Override
    public int insert(T t) {
        return mapper.insert(t);
    }

    @Override
    public int save(T t) {
        return mapper.insertSelective(t);
    }

    @Override
    public int insertUseGeneratedKeys(T t) {
        return mapper.insertUseGeneratedKeys(t);
    }

    @Override
    public int insertList(List<T> list) {
        return mapper.insertList(list);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateByConditionSelective(T record, Object condition) {
        return mapper.updateByConditionSelective(record,condition);
    }

    @Override
    public int updateByCondition(T record, Object condition) {
        return mapper.updateByCondition(record,condition);
    }

    @Override
    public int updateByExampleSelective(T record, Object example) {
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(T record, Object example) {
        return mapper.updateByExample(record, example);
    }

}
