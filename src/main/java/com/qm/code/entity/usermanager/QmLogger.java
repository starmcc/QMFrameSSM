package com.qm.code.entity.usermanager;

import java.util.Date;
/**
 * @author 浅梦工作室
 * @createDate 2018年9月29日 上午4:21:11
 * @Description 权限id
 */
public class QmLogger {
	/**
	 * 日志id
	 */
	private Long logId;
	/**
	 * 操作者
	 */
	private String operator;
	/**
	 * 日志
	 */
	private String text;
	/**
	 * 请求URL
	 */
	private String requestURL;
	/**
	 * 请求类
	 */
	private String requestClassName;
	/**
	 * 请求方法
	 */
	private String requestMethod;
	/**
	 * 请求值
	 */
	private String requestParam;
	/**
	 * 记录时间
	 */
	private Date createTime;
	/**
	 * 响应时间
	 */
	private Long responseTime;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getRequestClassName() {
		return requestClassName;
	}

	public void setRequestClassName(String requestClassName) {
		this.requestClassName = requestClassName;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

}
