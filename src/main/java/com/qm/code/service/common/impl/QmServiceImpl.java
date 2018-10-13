package com.qm.code.service.common.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.code.service.common.QmService;

import tk.mybatis.mapper.common.Mapper;
/**
 * @author 浅梦工作室
 * @createDate 2018年8月21日 上午2:06:20
 * @Description service封装实现类
 */
@Service("qmService")
public class QmServiceImpl<T> implements QmService<T> {

	@Autowired
	private Mapper<T> mapper;

	@Override
	public int add(T entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	public int add(List<T> entityList) {
		int num = 0;
		for (T t : entityList) {
			num += mapper.insertSelective(t);
		}
		return num;
	}

	@Override
	public int del(T entity) {
		return mapper.delete(entity);
	}

	@Override
	public int del(List<T> entityList) {
		int num = 0;
		for (T t : entityList) {
			num += mapper.delete(t);
		}
		return num;
	}

	@Override
	public int change(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<T> getList(T entity) {
		return mapper.select(entity);
	}

	@Override
	public T getOne(T entity) {
		return mapper.selectOne(entity);
	}

	@Override
	public int getCount(T entity) {
		return mapper.selectCount(entity);
	}
}
