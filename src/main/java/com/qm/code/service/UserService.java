package com.qm.code.service;

import com.qm.code.entity.User;

import java.util.List;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/2/1 6:02
 * @Description 用户接口
 */
public interface UserService {

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    User login(String userName,String password);
}
