package com.qm.code.security;

import com.qm.code.entity.Rights;
import com.qm.code.service.UserService;
import com.qm.frame.qmsecurity.basic.QmSecurityRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/1/31 18:44
 * @Description
 */
@Component
public class MyRealm implements QmSecurityRealm {

    @Autowired
    private UserService userService;

    @Override
    public List<String> authorizationPermissions(int roleId) {
        List<Rights> rightsList = userService.getRightsList(roleId);
        List<String> matchUrls = new ArrayList<>();
        for (Rights rights : rightsList) {
            matchUrls.add(rights.getMatchUrl());
        }
        return matchUrls;
    }


}
