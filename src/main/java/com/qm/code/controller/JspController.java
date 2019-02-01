package com.qm.code.controller;


import com.qm.frame.basic.controller.QmController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/2/2 3:45
 * @Description jsp控制器示例
 */
@Controller
@RequestMapping("/jsp")
public class JspController extends QmController {
    /**
     * helloQmFrame框架
     * @return
     */
    @GetMapping("/helloQmFrame")
    public String helloQmFrame(){
        super.request.setAttribute("msg","欢迎使用QmFrameSSM!");
        return "index";
    }

}
