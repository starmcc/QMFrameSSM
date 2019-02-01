package com.qm.code.controller;

import com.qm.code.entity.User;
import com.qm.code.service.UserService;
import com.qm.frame.basic.body.QmBody;
import com.qm.frame.basic.controller.QmCode;
import com.qm.frame.basic.controller.QmController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/2/2 3:57
 * @Description api接口示例
 */
@RestController
@RequestMapping("/api")
public class ApiController extends QmController {

    @Autowired
    private UserService userService;

    /**
     * 登录方法(Rest风格)
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public String login(@QmBody String userName,@QmBody String password){
        User user = userService.login(userName,password);
        if (user == null) {
            return super.sendJSON(QmCode._2,"账号或密码错误！",null);
        }
        return super.sendJSON(QmCode._1,user);
    }


}
