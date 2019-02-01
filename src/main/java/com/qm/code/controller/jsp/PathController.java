package com.qm.code.controller.jsp;

import com.qm.code.entity.Rights;
import com.qm.code.entity.User;
import com.qm.code.service.UserService;
import com.qm.frame.basic.body.QmBody;
import com.qm.frame.basic.controller.QmCode;
import com.qm.frame.basic.controller.QmController;
import com.qm.frame.qmsecurity.manager.QmSecurityManager;
import com.qm.frame.qmsecurity.manager.Qmbject;
import com.qm.frame.qmsecurity.note.QmPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/1/31 21:25
 * @Description TODO
 */
@Controller
@RequestMapping("/jsp")
public class PathController extends QmController {

    @Autowired
    private UserService userService;


    @QmPass
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @QmPass
    @PostMapping("/doLogin")
    @ResponseBody
    public String doLogin(@QmBody String userName,String password){
        // 数据库查询
        User user = userService.login(userName,password);
        if (user == null) {
            return super.sendJSON(QmCode._2,"用户名或密码错误!",null);
        }
        List<Rights> rightsList = userService.getRightsList(user.getRoleId());
        if (rightsList == null || rightsList.size() == 0) {
            return super.sendJSON(QmCode._2,"该用户无权限!",null);
        }
        List<String> matchUrls = new ArrayList<>();
        for (Rights rights : rightsList) {
            matchUrls.add(rights.getMatchUrl());
        }
        Qmbject qmbject = QmSecurityManager.getQmbject();
        qmbject.login(user,matchUrls);
        return super.sendJSON(QmCode._1);
    }

    @GetMapping("/index")
    public String index(){
        User user = (User) QmSecurityManager.getQmbject().getUserListener().getUser();
        request.setAttribute("user",user);
        return "index";
    }

}
