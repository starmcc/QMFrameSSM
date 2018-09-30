package com.qm.code.service.common;

import java.util.List;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月19日 下午8:48:09
 * @Description 描述 描述通用查询业务类
 * @param <T> 实体类
 */
public interface QmService<T> {
	/**
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 * @param entity
	 * @return
	 */
	int add(T entity);
	
	/**
	 * 批量保存实体，null的属性不会保存，会使用数据库默认值
	 * @param entity
	 * @return
	 */
	int add(List<T> entityList);
	
	/**
	 * 根据实体属性作为条件进行删除，查询条件使用等号
	 * @param entity
	 * @return
	 */
	int del(T entity);
	
	/**
	 * 根据实体属性作为条件进行<批量>删除，查询条件使用等号
	 * @param entity
	 * @return
	 */
	int del(List<T> entityList);
	
	/**
	 * 更新一个实体，根据主键更新属性不为null的值
	 * @return
	 */
	int change(T entity);
	
	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号，返回多条记录
	 * @return
	 */
	List<T> getList(T entity);
	
	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号，返回一条记录
	 * @return
	 */
	T getOne(T entity);
	
	/**
	 * 根据实体中的属性查询总数，查询条件使用等号
	 * @param entity
	 * @return
	 */
	int getCount(T entity); 
}
