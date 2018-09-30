package com.qm.code.service.usermanager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.code.dao.usermanager.QmUserManagerMapper;
import com.qm.code.entity.usermanager.QmLogger;
import com.qm.code.entity.usermanager.QmPower;
import com.qm.code.entity.usermanager.QmRole;
import com.qm.code.service.usermanager.QmUserManagerService;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月29日 上午2:13:18
 * @Description 用户管理器业务层实现类
 */
@Service
public class QmUserManagerServiceImpl implements QmUserManagerService {

	@Autowired
	private QmUserManagerMapper qmUserManagerMapper;

	@Override
	public QmRole getRole(String tableName, Integer roleId) {
		return qmUserManagerMapper.getRole(tableName, roleId);
	}

	@Override
	public List<QmPower> getPower(String tableName) {
		return qmUserManagerMapper.getPower(tableName);
	}

	@Override
	public Integer changeRole(String tableName, QmRole qmRole) {
		return qmUserManagerMapper.changeRole(tableName, qmRole);
	}

	@Override
	public Integer addRole(String tableName, QmRole qmRole) {
		return qmUserManagerMapper.addRole(tableName, qmRole);
	}

	@Override
	public Integer delRole(String tableName, Integer roleId) {
		return qmUserManagerMapper.delRole(tableName, roleId);
	}

	@Override
	public Integer addUserLogger(String tableName,QmLogger qmLogger) {
		return qmUserManagerMapper.addUserLogger(tableName,qmLogger);
	}

}
