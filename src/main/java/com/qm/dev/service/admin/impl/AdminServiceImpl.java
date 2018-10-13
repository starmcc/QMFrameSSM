package com.qm.dev.service.admin.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qm.code.service.common.impl.QmServiceImpl;
import com.qm.dev.dao.AdminMapper;
import com.qm.dev.entity.Admin;
import com.qm.dev.service.admin.AdminService;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月21日 上午1:32:07
 * @Description 拓展service 实现类 Demo
*/
@Service
public class AdminServiceImpl extends QmServiceImpl<Admin> implements AdminService{

	@Resource
	private AdminMapper adminMapper;
	
	@Override
	public List<Map<String, Object>> getTestList(Admin admin) {
		return adminMapper.getTestList(admin);
	}
}
