package com.qm.code.note;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月28日 下午11:59:23
 * @Description 用户管理器注解
 * @Param needLogin 是否需要登录 默认true
 * @Param log 日志文本
 * @Param logOpen 是否开启日志 默认false
 * @Param licence 所需权限id
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QmUserManagerAPI {
	/**
	 * 是否需要登录
	 * @return
	 */
	public boolean needLogin() default true;
	
	/**
	 * 记录log文本
	 * @return
	 */
    String log() default "无日志信息,程序员偷懒";
    
    /**
     * 日志开启
     * @return
     */
    boolean logOpen() default false;
    
    /**
     * 权限管理
     * @return
     */
    int licence() default -1;
}