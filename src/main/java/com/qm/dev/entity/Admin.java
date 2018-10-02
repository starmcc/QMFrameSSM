package com.qm.dev.entity;

/**
 * @author 浅梦工作室
 * @createDate 2018年10月3日 上午2:08:16
 * @Description 描述
 */
public class Admin {
	private Integer id;
	private String adminID;
	private String password;
	private Integer roleId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getId() {
		return id;
	}

	public String getAdminID() {
		return adminID;
	}

	public String getPassword() {
		return password;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
