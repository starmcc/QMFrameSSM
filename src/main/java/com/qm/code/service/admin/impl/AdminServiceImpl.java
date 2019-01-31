package com.qm.code.service.admin.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qm.code.dao.AdminMapper;
import com.qm.code.entity.Admin;
import com.qm.code.service.admin.AdminService;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月21日 上午1:32:07
 * @Description 拓展service 实现类 Demo
*/
@Service
public class AdminServiceImpl implements AdminService{

	@Resource
	private AdminMapper adminMapper;
	
	@Override
	public List<Map<String, Object>> getTestList(Admin admin) {
		return adminMapper.getTestList(admin);
	}
}
