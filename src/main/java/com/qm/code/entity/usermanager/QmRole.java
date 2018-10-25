package com.qm.code.entity.usermanager;

import java.util.Date;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月29日 上午1:37:31
 * @Description 角色实体
 */
public class QmRole {
	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者
	 */
	private String createOperator;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新者
	 */
	private String updateOperator;
	/**
	 * 权限id数组,逗号隔开
	 */
	private String powerIds;

	public String getPowerIds() {
		return powerIds;
	}

	public void setPowerIds(String powerIds) {
		this.powerIds = powerIds;
	}

	public String getRoleName() {
		return roleName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreateOperator() {
		return createOperator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getUpdateOperator() {
		return updateOperator;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUpdateOperator(String updateOperator) {
		this.updateOperator = updateOperator;
	}

}
