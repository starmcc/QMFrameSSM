package com.qm.code.aop;

import java.util.Arrays;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.qm.code.util.frame.QmUserManager;
import com.qm.code.util.logger.QmLog;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月19日 下午8:16:35
 * @Description 接口日志，返回请求时间，参数，返回值等信息。
 */
@Aspect // 等同于<aop:aspect ref="loggerWhole">
public @Component class LoggerWhole {

	/**
	 * 记录请求时间
	 */
	private Long starTime;
	
	@Resource
	private QmUserManager qmUserManager;

	// 切入点范围 所有controller
	@Pointcut("execution(* com.qm..*.controller..*.*(..))")
	public void qmPointcut() {
	}

	@Before("qmPointcut()")
	public void before(JoinPoint jp) {
		starTime = System.currentTimeMillis();
		StringBuffer logStr = new StringBuffer();
		// getTarget得到被代理的目标对象(要切入的目标对象)
		logStr.append("\n===================================================\n");
		logStr.append("请求定位:" + jp.getTarget().getClass().getName() + "\n");
		// getSignature得到被代理的目标对象的方法名(返回被切入的目标方法名)
		logStr.append("请求方法:【" + jp.getSignature().getName() + "】\n");
		// Arrays.toString(jp.getArgs())获得目标方法的参数列表
		logStr.append("参数列表:【" + Arrays.toString(jp.getArgs()) + "】\n");
		QmLog.debug(logStr.toString());
	}

	@AfterReturning(pointcut = "qmPointcut()", returning = "result")
	public void afterReturning(JoinPoint jp, Object result) {
		QmLog.debug("\n返回结果:【" + result + "】\n");
		Long endTime = System.currentTimeMillis();
		Long time = endTime - starTime;
		QmLog.info("\n==========请求响应耗时：" + time + "(毫秒)=========");
		qmUserManager.doAopQmUserManagerLogger(jp,time);
	}
	
	
}
