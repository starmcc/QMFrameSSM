package com.qm.code.security;

import com.qm.frame.qmsecurity.basic.QmSecurityRealm;
import org.springframework.stereotype.Component;

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


    @Override
    public List<String> authorizationPermissions(int roleId) {
        return null;
    }


}
