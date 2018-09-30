package com.qm.code.dao.usermanager;

import java.util.List;

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
	 * 获取角色
	 * 
	 * @param tableName
	 * @param roleId
	 * @return
	 */
	QmRole getRole(@Param("tableName") String tableName, @Param("roleId") Integer roleId);

	/**
	 * 修改角色
	 * 
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer changeRole(@Param("tableName") String tableName, @Param("role") QmRole qmRole);

	/**
	 * 获取权限
	 * 
	 * @param tableName
	 * @return
	 */
	List<QmPower> getPower(@Param("tableName") String tableName);

	/**
	 * 添加角色
	 * 
	 * @param tableName
	 * @param qmRole
	 * @return
	 */
	Integer addRole(@Param("tableName") String tableName, @Param("role") QmRole qmRole);

	/**
	 * 添加角色
	 * 
	 * @param tableName
	 * @param roleId
	 * @return
	 */
	Integer delRole(@Param("tableName") String tableName, @Param("roleId") Integer roleId);

	/**
	 * 添加日志
	 * 
	 * @return
	 */
	Integer addUserLogger(@Param("tableName") String tableName, @Param("qmLogger") QmLogger qmLogger);

}
