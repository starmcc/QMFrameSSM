package com.qm.code.dao.usermanager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qm.code.entity.usermanager.QmLogger;
import com.qm.code.entity.usermanager.QmPower;
import com.qm.code.entity.usermanager.QmRole;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月29日 上午2:14:02
 * @Description 用户管理器数据层
 */
public interface QmUserManagerMapper {

	/**
	 * 登录
	 * @param sql
	 * @param userName
	 * @param password
	 * @return
	 */
	Map<String, Object> login(@Param("sql") String sql, @Param("userName") String userName,
			@Param("password") String password);

	/**
	 * 添加角色
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer addRole(@Param("tableName") String tableName, @Param("role") QmRole qmRole);

	/**
	 * 删除角色
	 * @param tableName
	 * @param roleId
	 * @return
	 */
	Integer delRole(@Param("tableName") String tableName, @Param("roleId") Integer roleId);

	/**
	 * 修改角色
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer changeRole(@Param("tableName") String tableName, @Param("role") QmRole qmRole);

	/**
	 * 获取角色列表
	 * @param tableName
	 * @param roleId
	 * @return
	 */
	List<QmRole> getRole(@Param("tableName") String tableName, @Param("role") QmRole qmRole);

	/**
	 * 获取权限
	 * @param tableName
	 * @param qmPower
	 * @return
	 */
	List<QmPower> getPower(@Param("tableName") String tableName, @Param("power") QmPower qmPower);

	/**
	 * 根据powerIds获取权限列表
	 * @param tableName
	 * @param powerIds
	 * @return
	 */
	List<QmPower> getPowersById(@Param("tableName") String tableName, @Param("powerIds") List<Integer> powerIds);

	/**
	 * 添加日志
	 * @param tableName
	 * @param qmLogger
	 * @return
	 */
	Integer addUserLogger(@Param("tableName") String tableName, @Param("qmLogger") QmLogger qmLogger);

	/**
	 * 删除日志
	 * @param tableName
	 * @param qmLogger
	 * @return
	 */
	Integer delUserLogger(@Param("tableName") String tableName, @Param("logIds") Integer[] logIds);
	
	/**
	 * 获取日志列表
	 * @param tableName
	 * @param qmLogger
	 * @return
	 */
	List<QmLogger> getUserLogger(@Param("tableName") String tableName, @Param("log") QmLogger qmLogger);

}
