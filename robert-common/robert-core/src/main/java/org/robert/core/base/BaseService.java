package org.robert.core.base;

import java.util.List;

public interface BaseService<T> {

    int deleteByPrimaryKey(Object key);

    int deleteById(Object key);

    int deleteByIds(String s);

    int deleteByIds(List<Object> ids);

    int deleteByIds(Object[] ids);

    int delete(T record);

    int deleteByCondition(Object condition);

    int deleteByExample(Object example);

    T selectOne(T t);

    List<T> select(T t);

    List<T> selectByExample(Object o);

    List<T> selectByCondition(Object o);

    List<T> selectByIds(String ids);

    T selectById(Object key);

    boolean existsWithPrimaryKey(Object key);

    List<T> selectAll();

    int selectCount(T t);

    int selectCountByCondition(Object o);

    int selectCountByExample(Object o);

    T selectOneByExample(Object o);

    int insert(T t);

    int save(T t);

    int insertUseGeneratedKeys(T t);

    int insertList(List<T> list);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);

    int updateByConditionSelective(T record, Object condition);

    int updateByCondition(T record, Object condition);

    int updateByExampleSelective(T record, Object example);

    int updateByExample(T record, Object example);


}
