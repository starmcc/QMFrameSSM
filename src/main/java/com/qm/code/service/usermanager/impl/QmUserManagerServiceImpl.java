package com.qm.code.service.usermanager.impl;

import java.util.List;
import java.util.Map;

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
	public Map<String, Object> login(String sql, String userName, String password) {
		return qmUserManagerMapper.login(sql, userName, password);
	}

	@Override
	public Integer addTableRole(String tableName, QmRole qmRole) {
		return qmUserManagerMapper.addRole(tableName, qmRole);
	}

	@Override
	public Integer delTableRole(String tableName, Integer roleId) {
		return qmUserManagerMapper.delRole(tableName, roleId);
	}

	@Override
	public Integer changeTableRole(String tableName, QmRole qmRole) {
		return qmUserManagerMapper.changeRole(tableName, qmRole);
	}

	@Override
	public List<QmRole> getTableRole(String tableName, QmRole qmRole) {
		return qmUserManagerMapper.getRole(tableName, qmRole);
	}

	@Override
	public List<QmPower> getTablePower(String tableName, QmPower qmPower) {
		return qmUserManagerMapper.getPower(tableName, qmPower);
	}

	@Override
	public Integer addUserLogger(String tableName, QmLogger qmLogger) {
		return qmUserManagerMapper.addUserLogger(tableName, qmLogger);
	}

	@Override
	public Integer delUserLogger(String tableName, Integer[] logIds) {
		return qmUserManagerMapper.delUserLogger(tableName, logIds);
	}

	@Override
	public List<QmPower> getPowersById(String tableName, List<Integer> powerIds) {
		return qmUserManagerMapper.getPowersById(tableName, powerIds);
	}

	@Override
	public List<QmLogger> getUserLogger(String tableName, QmLogger qmLogger) {
		return qmUserManagerMapper.getUserLogger(tableName, qmLogger);
	}


}
