package com.qm.code.service.usermanager;

import java.util.List;
import java.util.Map;

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
	 * 登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	Map<String, Object> login(String sql, String userName, String password);

	/**
	 * 获取角色
	 * 
	 * @param tableName
	 * @param roleId
	 * @return
	 */
	List<QmRole> getRole(String tableName, QmRole qmRole);

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
	Integer changeTableRole(String tableName, QmRole qmRole);

	/**
	 * 添加库中角色
	 * 
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer addTableRole(String tableName, QmRole qmRole);

	/**
	 * 删除库中角色
	 * 
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer delTableRole(String tableName, Integer roleId);

	/**
	 * 添加日志
	 * 
	 * @param qmLogger
	 * @return
	 */
	Integer addUserLogger(String tableName, QmLogger qmLogger);

}
