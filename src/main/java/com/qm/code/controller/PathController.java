package com.qm.code.controller;

import com.qm.frame.qmsecurity.manager.QmSecurityManager;
import com.qm.frame.qmsecurity.note.QmPass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/1/31 21:25
 * @Description TODO
 */
@Controller
public class PathController{

    @QmPass
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request){
        String userName = QmSecurityManager.getQmbject().getTokenInfo().getUserName();
        request.setAttribute("userName",userName);
        return "login";
    }






}
