package com.qm.code.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.qm.code.util.frame.ApiUtil;
import com.qm.code.util.frame.QmUserManager;
import com.qm.code.util.frame.VersionUtil;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月19日 下午8:53:24
 * @Description 版本控制拦截器，依赖config.properties
 */
public class InitInterceptor implements HandlerInterceptor{

	@Resource
	private QmUserManager qmUserManager;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException{
		//版本控制
		boolean is = VersionUtil.verify(request);
		if(!is) {
			sendFilterJSON(response,301,"请求失败,目前不允许该版本进行请求!","");
			return false;
		}
		//登录控制
		is = qmUserManager.loginFilterVerify(handler);
		if(!is) {
			sendFilterJSON(response,302,"请求失败,登录状态校验失败!","");
			return false;
		}
		return true;
	}

	/**
	 * 拦截器返回json字符串到前端
	 * @param response
	 * @param code
	 * @param resultText
	 * @param data
	 * @throws IOException
	 */
	private static void sendFilterJSON(HttpServletResponse response,Integer code,String resultText,Object data) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(ApiUtil.sendJSON(code, resultText, data));
	}
	
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
}
