package com.qm.code.entity.usermanager;

/**
 * @author 浅梦工作室
 * @createDate 2018年10月3日 上午12:35:53
 * @Description
 */
public class Qmbject {
	/**
	 * 用户角色
	 */
	private QmRole qmRole;
	/**
	 * 用户对象
	 */
	private Object bean;
	

	public QmRole getQmRole() {
		return qmRole;
	}

	public void setQmRole(QmRole qmRole) {
		this.qmRole = qmRole;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

}
