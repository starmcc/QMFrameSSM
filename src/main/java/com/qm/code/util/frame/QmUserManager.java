package com.qm.code.util.frame;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import com.qm.code.entity.usermanager.QmLogger;
import com.qm.code.entity.usermanager.QmPower;
import com.qm.code.entity.usermanager.QmRole;
import com.qm.code.listener.QmLoginOnLine;
import com.qm.code.note.QmUserManagerAPI;
import com.qm.code.service.usermanager.QmUserManagerService;
import com.qm.code.util.io.PropertiesUtil;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月28日 下午11:59:23
 * @Description 用户管理器
 */
public @Component("qmUserManager") class QmUserManager {
	/**
	 * 用户管理器是否开启
	 */
	public static final String MANAGER_OPEN = PropertiesUtil.get("QmUserManager.open");
	/**
	 * 权限管理是否开启
	 */
	public static final String MANAGER_POWER_OPEN = PropertiesUtil.get("QmUserManager.power.open");
	/**
	 * 保存到session中的key名
	 */
	public static final String SESSION_KEY = PropertiesUtil.get("QmUserManager.session.key");
	/**
	 * 实际开发中用户实体类的账号字段名称(唯一账号字段)
	 */
	public static final String USERCODE_FIELD_NAME = PropertiesUtil.get("QmUserManager.usercode.field.name");
	/**
	 * 角色表在实际环境中的表名
	 */
	public static final String ROLE_TABLE_NAME = PropertiesUtil.get("QmUserManager.role.tableName");
	/**
	 * 权限表在实际环境中的表名
	 */
	public static final String POWER_TABLE_NAME = PropertiesUtil.get("QmUserManager.power.tableName");
	/**
	 * 用户日志表在实际环境中的表名
	 */
	public static final String USER_LOGGER_TABLE_NAME = PropertiesUtil.get("QmUserManager.userLogger.tableName");
	/**
	 * 存放在全局application的权限缓存key名(自定义)
	 */
	public static final String APPLICATION_POWER_KEY = PropertiesUtil.get("QmUserManager.application.power.key");
	/**
	 * 存放在全局application的用户列表key名(自定义)
	 */
	public static final String APPLICATION_LIST_KEY = PropertiesUtil.get("QmUserManager.application.list.key");
	/**
	 * 用户操作日志是否开启
	 */
	public static final String MANAGER_USER_LOGGER = PropertiesUtil.get("QmUserManager.user.logger");
	/**
	 * 监听工具
	 */
	private static QmLoginOnLine onLine;
	
	private HttpServletRequest request;
	
	public QmUserManager() {
		
	}
	
	@Resource
	private QmUserManagerService qmUserManagerService;

	/**
	 * 启动/更新用户对象和角色(登陆调用此方法)
	 * @param request
	 * @param bean
	 * @param roleId
	 * @return 如果找不到该角色返回-1,成功返回1,表字段错误返回0
	 */
	public int start(Object bean, int roleId) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
		if (onLine == null) {
			onLine = new QmLoginOnLine();
		}
		QmRole qmRole;
		try {
			qmRole = qmUserManagerService.getRole(ROLE_TABLE_NAME, roleId);
			if(qmRole == null) {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		onLine.setQmRole(qmRole);
		onLine.setObject(bean);
		session.setAttribute(SESSION_KEY, onLine);
		return 1;
	}

	/**
	 * 退出/登出用户(注销调用此方法)
	 * 
	 * @param request
	 * @param bean
	 * @param roleId
	 */
	public void exit() {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		session.invalidate();
	}

	/**
	 * 获取用户对象
	 * 
	 * @param request
	 * @return
	 */
	public Object getUser() {
		HttpSession session = request.getSession();
		onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
		if (onLine == null) {
			return null;
		}
		return onLine.getObject();
	}

	/**
	 * 获取用户角色
	 * 
	 * @param request
	 * @return
	 */
	public QmRole getRole() {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
		if (onLine == null) {
			return null;
		}
		return onLine.getQmRole();
	}

	/**
	 * 修改表中角色
	 * 
	 * @param request
	 * @param qmRole
	 * @return
	 */
	public QmRole changeRole(QmRole qmRole) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
		if (onLine == null) {
			return null;
		}
		int res = qmUserManagerService.changeRole(ROLE_TABLE_NAME, qmRole);
		if (res < 1) {
			return null;
		}
		onLine.setQmRole(qmRole);
		session.setAttribute(SESSION_KEY, onLine);
		return onLine.getQmRole();
	}

	/**
	 * 添加表中角色
	 * 
	 * @param request
	 * @param qmRole
	 * @return
	 */
	public QmRole addRole(QmRole qmRole) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
		if (onLine == null) {
			return null;
		}
		int res = qmUserManagerService.addRole(ROLE_TABLE_NAME, qmRole);
		if (res < 1) {
			return null;
		}
		onLine.setQmRole(qmRole);
		session.setAttribute(SESSION_KEY, onLine);
		return onLine.getQmRole();
	}

	/**
	 * 删除表中角色
	 * 
	 * @param request
	 * @param qmRole
	 * @return
	 */
	public boolean delRole(Integer roleId) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
		int res = qmUserManagerService.delRole(ROLE_TABLE_NAME, roleId);
		if (res < 1) {
			return false;
		}
		if (onLine.getQmRole() != null) {
			if (onLine.getQmRole().getRoleId() == roleId) {
				onLine.setQmRole(null);
				session.setAttribute(SESSION_KEY, onLine);
			}
		}
		return true;
	}

	/**
	 * 获取用户登录状态
	 * 
	 * @param request
	 * @return
	 */
	public boolean getLoginStatus(HttpServletRequest request) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
		if (onLine == null) {
			return false;
		}
		return true;
	}

	/**
	 * 获取权限表
	 * 
	 * @param request
	 * @return
	 */
	public List<QmPower> getPower(HttpServletRequest request) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		ServletContext application = request.getServletContext();
		@SuppressWarnings("unchecked")
		List<QmPower> qmUserManagerPowerList = (List<QmPower>) application.getAttribute(APPLICATION_POWER_KEY);
		if (qmUserManagerPowerList == null || qmUserManagerPowerList.size() == 0) {
			qmUserManagerPowerList = qmUserManagerService.getPower(POWER_TABLE_NAME);
			application.setAttribute(APPLICATION_POWER_KEY, qmUserManagerPowerList);
		}
		return qmUserManagerPowerList;
	}

	/**
	 * Filter登录控制,权限控制
	 * 
	 * @param request
	 * @param handler
	 * @return
	 */
	public boolean loginVerify(Object handler) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if (MANAGER_OPEN.trim().equals("true")) {
			if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				Method method = handlerMethod.getMethod();
				// 如果有这样的注释，则返回该注解的对象，否则null。
				QmUserManagerAPI qmUserManagerAPI = method.getAnnotation(QmUserManagerAPI.class);
				// 这里判断如果有注解的话,等于需要登录
				if (qmUserManagerAPI != null && qmUserManagerAPI.needLogin()) {
					boolean loginStatus = getLoginStatus(request);
					if (!loginStatus) {
						return false;
					}
					// 进入权限控制
					if (MANAGER_POWER_OPEN.trim().equals("true")) {
						if (qmUserManagerAPI.licence() != -1) {
							QmRole qmRole = getRole();
							// 超级管理员是*
							if (!qmRole.getPowerIds().trim().equals("*")) {
								String[] userPowers = qmRole.getPowerIds().split(",");
								if (userPowers == null || userPowers.length == 0) {
									// 无权限哦
									return false;
								}
								// 转int数组
								int[] ids = new int[userPowers.length];
								for (int i = 0; i < userPowers.length; i++) {
									ids[i] = Integer.parseInt(userPowers[i]);
								}
								// 取出用户角色中的权限数组，并对比是否存在licence的权限
								for (int i = 0; i < ids.length; i++) {
									if (qmUserManagerAPI.licence() == ids[i]) {
										break;
									}
									if (i + 1 >= ids.length) {
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	
	/**
	 * 获取当前正在登录的用户集合(可获取在线列表数,在线列表等)
	 * @param request
	 * @return
	 */
	public List<Object> getApplicationQmUser(HttpServletRequest request){
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		ServletContext application = request.getServletContext();
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) application.getAttribute(APPLICATION_LIST_KEY);
		return list;
	}
	
	
	/**
	 * 在AOP中打印日志
	 * @param jp
	 * @return
	 */
	public void doAopQmUserManagerLogger(JoinPoint jp,long time) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if(!MANAGER_USER_LOGGER.trim().equals("true")) {
			return;
		}
		MethodSignature MethodSignature = (MethodSignature)jp.getSignature();
		Method method = MethodSignature.getMethod();
		QmUserManagerAPI qmUserManagerAPI = method.getAnnotation(QmUserManagerAPI.class);
		if(qmUserManagerAPI == null) {
			return;
		}
		if(qmUserManagerAPI.logOpen()) {
			QmLogger qmLogger = new QmLogger();
			qmLogger.setOperator(null);
			qmLogger.setRequestClassName(jp.getTarget().getClass().getName());
			qmLogger.setRequestMethod(method.getName());
			qmLogger.setRequestParam(Arrays.toString(jp.getArgs()));
			qmLogger.setRequestURL(request.getRequestURL().toString());
			qmLogger.setText(qmUserManagerAPI.log());
			Object user = getUser();
			String userCode = ApiUtil.getFieldValueByFieldName(USERCODE_FIELD_NAME, user).toString();
			qmLogger.setOperator(userCode);
			qmLogger.setCreateTime(new Date());
			qmLogger.setResponseTime(time);
			qmUserManagerService.addUserLogger(USER_LOGGER_TABLE_NAME, qmLogger);
		}
	}
	
}
