package com.qm.code.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.qm.code.entity.usermanager.QmRole;
import com.qm.code.util.io.PropertiesUtil;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月19日 下午8:53:34
 * @Description session用户登录/下线感知监听
 */
@SuppressWarnings("unchecked")
public class QmLoginOnLine implements HttpSessionBindingListener {
	/**
	 * 
	 */
	public static final String APPLICATION_LIST_KEY = PropertiesUtil.get("QmUserManager.application.list.key");

	/**
	 * 该属性为用户对象
	 */
	private Object object = null;
	/**
	 * 该属性为角色id
	 */
	private QmRole qmRole = null;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public QmRole getQmRole() {
		return qmRole;
	}

	public void setQmRole(QmRole qmRole) {
		this.qmRole = qmRole;
	}

	public void valueBound(HttpSessionBindingEvent event) {
		// 绑定时触发
		// --------------------业务处理------------------
		ServletContext application = event.getSession().getServletContext();
		List<Object> loginLis = (List<Object>) application.getAttribute(APPLICATION_LIST_KEY);
		if (loginLis == null) {
			loginLis = new ArrayList<Object>();
		}
		loginLis.add(object);
		application.setAttribute(APPLICATION_LIST_KEY, object);
		// ------------------业务处理---------------------
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		// 解绑时触发
		// ------------------业务处理---------------------
		ServletContext application = event.getSession().getServletContext();
		List<Object> userList = (List<Object>) application.getAttribute(APPLICATION_LIST_KEY);
		if (userList != null) {
			userList.remove(object);
		}
		// 可在此添加业务处理

		// -----------------
		application.setAttribute(APPLICATION_LIST_KEY, userList);
		// ------------------业务处理---------------------
	}
}