package com.qm.dev.service.testadmin.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qm.code.service.common.impl.QmServiceImpl;
import com.qm.dev.dao.TestAdminMapper;
import com.qm.dev.entity.TestAdmin;
import com.qm.dev.service.testadmin.TestAdminService;

@Service
public class TestAdminServiceImpl extends QmServiceImpl<TestAdmin> implements TestAdminService{
	
	@Resource
	private TestAdminMapper testAdminMapper;
}
