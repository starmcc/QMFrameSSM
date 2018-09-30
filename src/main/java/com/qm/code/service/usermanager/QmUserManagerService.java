package com.qm.code.service.usermanager;

import java.util.List;

import com.qm.code.entity.usermanager.QmLogger;
import com.qm.code.entity.usermanager.QmPower;
import com.qm.code.entity.usermanager.QmRole;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月29日 上午2:07:25
 * @Description 用户管理器业务层
 */
public interface QmUserManagerService {
	/**
	 * 获取用户角色
	 * 
	 * @param tableName
	 * @param roleId
	 * @return
	 */
	QmRole getRole(String tableName, Integer roleId);

	/**
	 * 获取用户角色权限列表
	 * 
	 * @param tableName
	 * @return
	 */
	List<QmPower> getPower(String tableName);

	/**
	 * 修改库中角色
	 * 
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer changeRole(String tableName, QmRole qmRole);

	/**
	 * 添加库中角色
	 * 
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer addRole(String tableName, QmRole qmRole);

	/**
	 * 删除库中角色
	 * 
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer delRole(String tableName, Integer roleId);
	
	/**
	 * 添加日志
	 * @param qmLogger
	 * @return
	 */
	Integer addUserLogger(String tableName,QmLogger qmLogger);
	
}
