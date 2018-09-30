package com.qm.code.util.logger;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月23日 上午1:07:41
 * @Description 日志输出
*/
public class QmLog{

	private QmLog() {};
	
	/**
	 * logger
	 */
	public static final  Logger LOG;
	
	static {
		LOG = Logger.getLogger(QmLog.class);
	}
	
	public static void debug(Object text) {
		LOG.debug(text);
	}
	
	public static void info(Object text) {
		LOG.info(text);
	}
	
	public static void warn(Object text) {
		LOG.warn(text);
	}
	
	public static void error(Object text) {
		LOG.error(text);
	}
	
	public static void debugToBean(Object bean) {
		bean = JSON.toJSONString(bean);
		LOG.debug(bean);
	}
	
	public static void infoToBean(Object bean) {
		bean = JSON.toJSONString(bean);
		LOG.info(bean);
	}
	
	public static void warnToBean(Object bean) {
		bean = JSON.toJSONString(bean);
		LOG.warn(bean);
	}
	
	public static void errorToBean(Object bean) {
		bean = JSON.toJSONString(bean);
		LOG.error(bean);
	}
	
}
