package com.qm.code.service.impl;

import com.qm.code.dao.RightsMapper;
import com.qm.code.dao.UserMapper;
import com.qm.code.entity.Rights;
import com.qm.code.entity.User;
import com.qm.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/2/1 6:03
 * @Description 用户业务实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RightsMapper rightsMapper;

    @Override
    public User login(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        return userMapper.selectOne(user);
    }

    @Override
    public List<Rights> getRightsList(Integer roleId) {
        Rights rights = new Rights();
        rights.setRoleId(roleId);
        return rightsMapper.select(rights);
    }
}
