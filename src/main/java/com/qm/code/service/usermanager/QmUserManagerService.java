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
	 * @param userName
	 * @param password
	 * @return
	 */
	Map<String,Object> login(String sql, String userName, String password);

	/**
	 * 添加库中角色
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer addTableRole(String tableName, QmRole qmRole);

	/**
	 * 删除库中角色
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer delTableRole(String tableName, Integer roleId);

	/**
	 * 修改库中角色
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer changeTableRole(String tableName, QmRole qmRole);

	/**
	 * 获取角色
	 * @param tableName
	 * @param roleId
	 * @return
	 */
	List<QmRole> getTableRole(String tableName, QmRole qmRole);

	/**
	 * 获取权限列表
	 * @param tableName
	 * @param qmPower
	 * @return
	 */
	List<QmPower> getTablePower(String tableName, QmPower qmPower);

	/**
	 * 根据powerIds获取权限列表
	 * @param tableName
	 * @param powerIds
	 * @return
	 */
	List<QmPower> getPowersById(String tableName, List<Integer> powerIds);
	
	/**
	 * 添加日志
	 * @param tableName
	 * @param qmLogger
	 * @return
	 */
	Integer addUserLogger(String tableName, QmLogger qmLogger);
	
	/**
	 * 删除日志
	 * @param tableName
	 * @param qmLogger
	 * @return
	 */
	Integer delUserLogger(String tableName, Integer[] logIds);
	
	/**
	 * 获取日志列表
	 * @param tableName
	 * @param qmLogger
	 * @return
	 */
	List<QmLogger> getUserLogger(String tableName,QmLogger qmLogger);
	
	
}
