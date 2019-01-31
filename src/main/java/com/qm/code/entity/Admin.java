package com.qm.code.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * @author 浅梦工作室
 * @createDate 2018年10月3日 上午2:08:16
 * @Description 实体类范例
 */
@Table(name = "qm_admin")
@NameStyle(Style.normal)
public class Admin {

	@Id
	private Integer id;
	private String adminID;
	private String password;
	private Integer roleId;
	@Transient
	private String tttt; // 不是数据库表中的字段

	public String getTttt() {
		return tttt;
	}

	public void setTttt(String tttt) {
		this.tttt = tttt;
	}

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
