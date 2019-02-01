package com.qm.code.controller.api;

import com.qm.code.entity.User;
import com.qm.code.service.UserService;
import com.qm.frame.basic.body.QmBody;
import com.qm.frame.basic.controller.QmCode;
import com.qm.frame.basic.controller.QmController;
import com.qm.frame.qmsecurity.entity.QmTokenInfo;
import com.qm.frame.qmsecurity.manager.QmSecurityManager;
import com.qm.frame.qmsecurity.manager.Qmbject;
import com.qm.frame.qmsecurity.note.QmPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/2/1 6:13
 * @Description TODO
 */
@RestController
@RequestMapping("/api")
public class ApiController extends QmController {

    @Autowired
    private UserService userService;

    @QmPass
    @PostMapping("/login")
    public String login(@QmBody String userName, @QmBody String password){
        // 数据库查询
        User user = userService.login(userName,password);
        if (user == null) {
            return super.sendJSON(QmCode._2,"登录失败!",null);
        }
        // 安全框架使用
        Qmbject qmbject = QmSecurityManager.getQmbject();
        QmTokenInfo qmTokenInfo = new QmTokenInfo();
        qmTokenInfo.setUserName(user.getUserName());
        qmTokenInfo.setRoleId(user.getRoleId());
        Map<String,String> infoMap = new HashMap<>();
        infoMap.put("loginTime",new Date().toString());
        qmTokenInfo.setInfoMap(infoMap);
        // 登录有效10分钟
        String token = qmbject.login(qmTokenInfo,60 * 10);
        return super.sendJSON(QmCode._1,token);
    }
}
