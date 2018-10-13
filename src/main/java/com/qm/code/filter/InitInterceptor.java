package com.qm.code.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.qm.code.util.frame.ApiUtil;
import com.qm.code.util.frame.QmUserManager;
import com.qm.code.util.frame.VersionUtil;
import com.qm.code.util.io.PropertiesUtil;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月19日 下午8:53:24
 * @Description 版本控制拦截器，依赖config.properties
 */
public class InitInterceptor implements HandlerInterceptor{

	@Resource
	private QmUserManager qmUserManager;
	
	private static final String REST_VIEW = PropertiesUtil.get("qmframe.restOrView");
	
	private static final String QMFRAME_FILTER_VIEWNAME = PropertiesUtil.get("qmframe.filter.viewName");
	
	private static final String QMFRAME_REQUEST_PATH_KEY = PropertiesUtil.get("qmframe.request.path.key");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException{
		//项目根路径
		setPath(request);
		//版本控制
		boolean is = VersionUtil.verify(request);
		if(!is) {
			sendFilter(request,response,301,"请求失败,目前不允许该版本进行请求!","");
			return false;
		}
		//登录控制
		is = qmUserManager.loginFilterVerify(handler);
		if(!is) {
			sendFilter(request,response,302,"请求失败,登录状态校验失败!","");
			return false;
		}
		return true;
	}
	
	/**
	 * 存放项目路径
	 * @param request
	 */
	private static void setPath(HttpServletRequest request) {
		if(REST_VIEW.trim().equals("view")) {
			String path = request.getContextPath();
			StringBuffer basePathSb = new StringBuffer();
			basePathSb.append(request.getScheme());
			basePathSb.append("://");
			basePathSb.append(request.getServerName());
			basePathSb.append(":");
			basePathSb.append(request.getServerPort());
			basePathSb.append(path);
			basePathSb.append("/");
			request.setAttribute(QMFRAME_REQUEST_PATH_KEY, basePathSb.toString());
		}
	}

	/**
	 * 拦截器返回json字符串到前端
	 * @param response
	 * @param code
	 * @param resultText
	 * @param data
	 * @throws Exception
	 */
	private static void sendFilter(HttpServletRequest request,HttpServletResponse response,Integer code,String msg,Object data){
		try {
			if(REST_VIEW.trim().equals("view")) {
				ApiUtil.sendRequestView(request, code, msg, data);
				request.getRequestDispatcher(QMFRAME_FILTER_VIEWNAME).forward(request,response);
			}else {
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(ApiUtil.sendJSON(code, msg, data));
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
