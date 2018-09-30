package com.qm.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月20日 上午12:07:47
 * @Description JSP控制器
*/
@Controller
@RequestMapping("/test2")
public class ModelTestController {

	/**
	 * Demo测试返回jsp视图层
	 * @param model
	 * @param userName
	 * @return
	 */
	@RequestMapping("/demo")
	public String demo(Model model , String userName) {
		model.addAttribute("value","Hello,Word");
		return "/jsp/index";
	}
}