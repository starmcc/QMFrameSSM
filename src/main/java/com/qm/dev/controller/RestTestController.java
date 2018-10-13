package com.qm.dev.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qm.code.entity.usermanager.QmRole;
import com.qm.code.entity.usermanager.Qmbject;
import com.qm.code.note.QmUserManagerAPI;
import com.qm.code.util.baidu.BaiduStatisticsUtil;
import com.qm.code.util.frame.ApiUtil;
import com.qm.code.util.frame.QmUserManager;
import com.qm.code.util.logger.QmLog;
import com.qm.dev.entity.Admin;
import com.qm.dev.service.admin.AdminService;
import com.qm.dev.service.testadmin.TestAdminService;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月19日 下午8:15:55
 * @Description 测试Rest接口
 */
@RestController
@RequestMapping("/test")
public class RestTestController{
	
	//注入通用service
	@Resource
	private AdminService adminService;
	@Resource
	private QmUserManager qmUserManager;
	@Resource
	private TestAdminService TestAdminService;
	
	/**
	 * 接口demo
	 * @param value
	 * @return
	 */
	@PostMapping("/demo")
	public String demo1(@RequestBody String value) {
		QmLog.debug("testController进入");
		Map<String,Object> map = ApiUtil.getJsonValueDesMap(value);
		//参数判断封装类
		boolean is = ApiUtil.IsEndNullMap(map, "text","userName");
		if(!is) {
			return ApiUtil.sendJSON(ApiUtil.CODE_PARAM_ERROR, ApiUtil.errorMsg + "参数错误", "");
		}
		return ApiUtil.sendJSON(ApiUtil.CODE_SUCCESS, "访问成功", "HelloWord");
	}
	
	/**
	 * 登录Demo接口
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/demo2")
	@QmUserManagerAPI(needLogin=false,logOpen=true,log="测试")
	public String demo2(HttpServletRequest request) throws Exception {
		//此为封装好的登录+监听工具类，直接调用即可登录
		Qmbject qmbject = qmUserManager.login(Admin.class, "admin", "admin");
		if(qmbject == null) {
			return ApiUtil.sendJSON(ApiUtil.CODE_DEFEATED, "未设置该用户的角色", null);
		}
		Admin admin = (Admin) qmbject.getBean();
		QmRole qmRole = new QmRole();
		qmRole.setRoleId(admin.getRoleId());
		qmbject.setQmRole(qmRole);
		qmUserManager.updateQmbject(qmbject);
		//登录期间可直接调用get方法获取登录用户信息,如果没登录直接为空。
		return ApiUtil.sendJSON(ApiUtil.CODE_DEFEATED, "登录成功", qmbject);
	}

	/**
	 * 登录Demo接口
	 * @param session
	 * @return
	 */
	@GetMapping("/demo3")
	@QmUserManagerAPI(licence=1)
	public String demo3(HttpServletRequest request) {
		return ApiUtil.sendJSON(ApiUtil.CODE_SUCCESS, "通过", "ok");
	}
	/**
	 * 测试自定义查询业务调用 + pageHelper分页实例
	 * @return
	 */
	@GetMapping("/demo4")
	public String demo4() {
		PageHelper.startPage(1,10);
		List<Map<String,Object>> map = adminService.getTestList(null);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(map);
		return ApiUtil.sendJSON(ApiUtil.CODE_SUCCESS, "查询成功", pageInfo);
	}


	
	@GetMapping("/demo5")
	public String demo5() {
		String addr1="广州";
		String addr2="深圳";
		Integer distance= BaiduStatisticsUtil.getSeparation(addr1, addr2);
		QmLog.debug(addr1 + "到" + addr2 + "的distance----->"+ distance + " KM");
		return ApiUtil.sendJSON(ApiUtil.CODE_SUCCESS, "查询成功", distance);
	}
	
}