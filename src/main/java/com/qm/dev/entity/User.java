package com.qm.dev.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月19日 下午8:17:24
 * @Description 实体类测试模板
 */
@NameStyle(Style.normal)
@Table(name = "qm_user") // 指定该实体类对应的数据库表
public class User {
	@Id // 指定该列的主键
	private Long id;
	private String userName;
	private String password;
	private Integer roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
