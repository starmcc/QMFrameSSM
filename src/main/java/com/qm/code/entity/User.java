package com.qm.code.entity;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/2/1 5:58
 * @Description 用户实体
 */
@Table(name = "qm_user")
@NameStyle(Style.normal)
public class User {
    @Id
    private Integer userId;
    private String userName;
    private String password;
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
