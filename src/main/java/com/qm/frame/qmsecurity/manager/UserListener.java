package com.qm.frame.qmsecurity.manager;

import com.qm.frame.qmsecurity.config.QmSecurityContent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.ArrayList;
import java.util.List;
/**
*    BindingListener实例类
*/
public class UserListener implements HttpSessionBindingListener{

	private Object user = null;

	private List<String> matchUrls = null;

	public List<String> getMatchUrls() {
		return matchUrls;
	}

	public void setMatchUrls(List<String> matchUrls) {
		this.matchUrls = matchUrls;
	}

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	public void valueBound(HttpSessionBindingEvent event) {
		// 绑定时触发
        //--------------------业务处理------------------
        ServletContext application = event.getSession().getServletContext();
        List<Object> userList = (List<Object>)application.getAttribute("userList");
		if(userList == null){
            userList = new ArrayList<Object>();
        }
        userList.add(user);
        application.setAttribute("userList",userList);
        //------------------业务处理---------------------
		QmSecurityContent.SECURITY_SESSION_REALM.inUser(user);
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		// 解绑时触发
          //------------------业务处理---------------------
		ServletContext application = event.getSession().getServletContext();
        List<Object> userList = (List<Object>)application.getAttribute("userList");
		if(userList != null){
            userList.remove(user);
        }
        application.setAttribute("userList",userList);
          //------------------业务处理---------------------
		QmSecurityContent.SECURITY_SESSION_REALM.outUser(user);
	}
}