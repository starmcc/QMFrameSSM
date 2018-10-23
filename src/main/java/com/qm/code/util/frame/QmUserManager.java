package com.qm.code.util.frame;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.qm.code.entity.usermanager.Qmbject;
import com.qm.code.listener.QmLoginOnLine;
import com.qm.code.note.QmUserManagerAPI;
import com.qm.code.service.usermanager.QmUserManagerService;
import com.qm.code.util.io.PropertiesUtil;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月28日 下午11:59:23
 * @Description QM用户管理器
 */
public @Component class QmUserManager {
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
	 * 登录配置sql方案
	 */
	public String LOGIN_SQL = PropertiesUtil.get("QmUserManager.login.sql");
	/**
	 * 监听器
	 */
	private QmLoginOnLine onLine;
	
	/**
	 * request作用域
	 */
	private HttpServletRequest request;

	public QmUserManager() {

	}

	/**
	 * 服务所需调用service
	 */
	@Resource
	private QmUserManagerService qmUserManagerService;
	
	/**
	 * 登录
	 * @param bean
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public <T> Qmbject login(Class<T> clamm, String userName, String password){
		try {
			if (LOGIN_SQL == null) {
				return null;
			}
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
			if (onLine == null) {
				onLine = new QmLoginOnLine();
			}
			Map<String,Object> map = qmUserManagerService.login(LOGIN_SQL, userName, password);
			if(map == null) {
				return null;
			}
			T tBean = ApiUtil.mapToBean(map,clamm);
			if (tBean == null) {
				return null;
			}
			Qmbject qmbject = new Qmbject();
			qmbject.setBean(tBean);
			onLine.setQmbject(qmbject);
			session.setAttribute(SESSION_KEY, onLine);
			return qmbject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 更新用户对象
	 * @param qmbject
	 * @return 如果找不到该角色返回-1,成功返回1,表字段错误返回0,-2,异常-3
	 */
	public int updateQmbject(Qmbject qmbject) {
		try {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
			if (onLine == null) {
				return -2;
			}
			if (qmbject.getQmRole() != null) {
				try {
					List<QmRole> qmRoleLis = qmUserManagerService.getTableRole(ROLE_TABLE_NAME, qmbject.getQmRole());
					if (qmRoleLis == null || qmRoleLis.size() == 0) {
						return -1;
					}
					qmbject.setQmRole(qmRoleLis.get(0));
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
			}
			onLine.setQmbject(qmbject);
			session.setAttribute(SESSION_KEY, onLine);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -3;
		}
	}

	/**
	 * 退出/登出用户(注销调用此方法)
	 */
	public void exit() {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		session.invalidate();
	}

	/**
	 * 获取登录对象
	 * @return
	 */
	public Qmbject getQmbject() {
		HttpSession session = request.getSession();
		onLine = (QmLoginOnLine) session.getAttribute(SESSION_KEY);
		if (onLine == null) {
			return null;
		}
		return onLine.getQmbject();
	}

	/**
	 * 添加表中角色
	 * @param qmRole
	 * @return
	 */
	public boolean addTableRole(QmRole qmRole) {
		int res = qmUserManagerService.addTableRole(ROLE_TABLE_NAME, qmRole);
		if (res < 1) {
			return false;
		}
		return true;
	}

	/**
	 * 修改表中角色
	 * @param qmRole
	 * @return
	 */
	public boolean changeTableRole(QmRole qmRole) {
		int res = qmUserManagerService.changeTableRole(ROLE_TABLE_NAME, qmRole);
		if (res < 1) {
			return false;
		}
		return true;
	}


	/**
	 * 删除表中角色
	 * @param qmRole
	 * @return
	 */
	public boolean delTableRole(Integer roleId) {
		int res = qmUserManagerService.delTableRole(ROLE_TABLE_NAME, roleId);
		if (res < 1) {
			return false;
		}
		return true;
	}

	/**
	 * 获取表中角色
	 * @param qmRole
	 * @return
	 */
	public List<QmRole> getTableRoleList(QmRole qmRole) {
		return qmUserManagerService.getTableRole(ROLE_TABLE_NAME, qmRole);
	}

	
	/**
	 * 获取权限表
	 * @return
	 */
	public List<QmPower> getPower() {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		ServletContext application = request.getServletContext();
		@SuppressWarnings("unchecked")
		List<QmPower> qmUserManagerPowerList = (List<QmPower>) application.getAttribute(APPLICATION_POWER_KEY);
		if (qmUserManagerPowerList == null || qmUserManagerPowerList.size() == 0) {
			qmUserManagerPowerList = qmUserManagerService.getTablePower(POWER_TABLE_NAME,null);
			application.setAttribute(APPLICATION_POWER_KEY, qmUserManagerPowerList);
		}
		return qmUserManagerPowerList;
	}
	
	/**
	 * 根据角色获取权限列表
	 * @param qmRole
	 * @return
	 */
	public List<QmPower> getRolePower(QmRole qmRole){
		List<QmRole> qmRoles = qmUserManagerService.getTableRole(ROLE_TABLE_NAME, qmRole);
		if(qmRoles == null || qmRoles.size() == 0) {
			return null;
		}
		qmRole = qmRoles.get(0);
		Integer[] powers = qmRole.getPowerIds();
		return qmUserManagerService.getPowersById(POWER_TABLE_NAME,powers);
	}
	
	/**
	 * 为角色添加权限
	 * @Param qmRole
	 * @param powerIds
	 * @return
	 */
	public boolean addRolePower(QmRole qmRole,Integer[] powerIds) {
		List<QmRole> qmRoles = qmUserManagerService.getTableRole(ROLE_TABLE_NAME, qmRole);
		if (qmRoles == null || qmRoles.size() == 0) {
			return false;
		}
		Integer[] rolePowerIds = qmRoles.get(0).getPowerIds();
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(rolePowerIds));
        list.addAll(Arrays.asList(powerIds));
        List<Integer> listTemp = new ArrayList<Integer>();  
        for(int i=0;i<list.size();i++){  
            if(!listTemp.contains(list.get(i))){  
                listTemp.add(list.get(i));  
            }  
        }
        StringBuffer ids = new StringBuffer();
        for (int i = 0; i < listTemp.size(); i++) {
        	if(listTemp.get(i) == -1) {
        		ids = new StringBuffer("-1");
        		break;
        	}
        	ids.append(listTemp.get(i).toString());
		}
        qmRole.setPowerIds(ids.toString());
        int res = qmUserManagerService.changeTableRole(ROLE_TABLE_NAME, qmRole);
        if (res < 1) {
			return false;
		}
        return true;
	}
	
	/**
	 * Filter登录控制,权限控制
	 * @param handler
	 * @return
	 */
	public boolean loginFilterVerify(Object handler) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if (MANAGER_OPEN.trim().equals("true")) {
			if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				Method method = handlerMethod.getMethod();
				// 如果有这样的注释，则返回该注解的对象，否则null。
				QmUserManagerAPI qmUserManagerAPI = method.getAnnotation(QmUserManagerAPI.class);
				// 这里判断如果有注解的话,等于需要登录
				Qmbject qmbject = getQmbject();
				if (qmUserManagerAPI != null && qmUserManagerAPI.needLogin()) {
					if (qmbject == null) {
						return false;
					}
					// 进入权限控制
					if (MANAGER_POWER_OPEN.trim().equals("true")) {
						QmRole qmRole = qmbject.getQmRole();
						return valueOfPower(qmRole.getPowerIds(), qmUserManagerAPI.licence());
					}
				}
			}
		}
		return true;
	}

	/**
	 * 获取当前正在登录的用户集合(可获取在线列表数,在线列表等)
	 * @return
	 */
	public List<Object> getApplicationQmUser() {
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
	public void doAopQmUserManagerLogger(JoinPoint jp, long time) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if (!MANAGER_USER_LOGGER.trim().equals("true")) {
			return;
		}
		MethodSignature MethodSignature = (MethodSignature) jp.getSignature();
		Method method = MethodSignature.getMethod();
		QmUserManagerAPI qmUserManagerAPI = method.getAnnotation(QmUserManagerAPI.class);
		if (qmUserManagerAPI == null) {
			return;
		}
		if (qmUserManagerAPI.logOpen()) {
			QmLogger qmLogger = new QmLogger();
			qmLogger.setOperator(null);
			qmLogger.setRequestClassName(jp.getTarget().getClass().getName());
			qmLogger.setRequestMethod(method.getName());
			qmLogger.setRequestParam(Arrays.toString(jp.getArgs()));
			qmLogger.setRequestURL(request.getRequestURL().toString());
			qmLogger.setText(qmUserManagerAPI.log());
			if(qmUserManagerAPI.needLogin()){
				Object user = getQmbject().getBean();
				String userCode = ApiUtil.getFieldValueByFieldName(USERCODE_FIELD_NAME, user).toString();
				qmLogger.setOperator(userCode);
			}
			qmLogger.setCreateTime(new Date());
			qmLogger.setResponseTime(time);
			qmUserManagerService.addUserLogger(USER_LOGGER_TABLE_NAME, qmLogger);
		}
	}
	
	/**
	 * 手动添加日志
	 * @param qmLogger
	 * @return
	 */
	public boolean addUserLogger(QmLogger qmLogger) {
		int res = qmUserManagerService.addUserLogger(USER_LOGGER_TABLE_NAME, qmLogger);
		if (res < 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 删除日志
	 * @param qmLogger
	 * @return
	 */
	public boolean delUserLogger(Integer[] logIds) {
		int res = qmUserManagerService.delUserLogger(USER_LOGGER_TABLE_NAME,logIds);
		if (res < 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取日志列表
	 * @param qmLogger
	 * 该方法提供较为全面的搜索功能
	 * 实体类中只有logId是绝对相等的判断
	 * 其余字段均为模糊搜索
	 * 时间搜索时,会检索出设置时间之后的所有日志
	 * @return
	 */
	public List<QmLogger> getUserLogger(QmLogger qmLogger) {
		return qmUserManagerService.getUserLogger(USER_LOGGER_TABLE_NAME,qmLogger);
	}
	
	
	/**
	 * 判断权限数组中是否存在该权限Id
	 * @param powerIds
	 * @param powerIdTemp
	 * @return
	 */
	public boolean valueOfPower(Integer[] powerIds,Integer powerIdTemp) {
		if(powerIds == null || powerIds.length == 0) {
			// 无权限哦
			return false;
		}
		// 超级管理员是-1
		for (int i = 0; i < powerIds.length; i++) {
			if(powerIds[i] == -1) {
				break;
			}
			if (powerIdTemp == powerIds[i]) {
				break;
			}
			if (i + 1 >= powerIds.length) {
				return false;
			}
		}
		return true;
	}
}
