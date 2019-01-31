package com.qm.code.service.testadmin.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qm.code.dao.TestAdminMapper;
import com.qm.code.entity.TestAdmin;
import com.qm.code.service.testadmin.TestAdminService;

@Service
public class TestAdminServiceImpl implements TestAdminService{
	
	@Resource
	private TestAdminMapper testAdminMapper;
}
