package com.qm.code.entity;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/2/1 15:09
 * @Description 权限实体
 */
@Table(name = "qm_rights")
@NameStyle(Style.normal)
public class Rights {
    @Id
    private Integer rightsId;
    private String matchUrl;
    private Integer roleId;

    public Integer getRightsId() {
        return rightsId;
    }

    public void setRightsId(Integer rightsId) {
        this.rightsId = rightsId;
    }

    public String getMatchUrl() {
        return matchUrl;
    }

    public void setMatchUrl(String matchUrl) {
        this.matchUrl = matchUrl;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
