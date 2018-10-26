package com.qm.code.util.frame;

import org.apache.ibatis.annotations.Case;

/**
 * @author 状态码工具类
 * @createDate 2018年10月26日 上午12:58:22
 * @Description 数据请求、返回工具类
 */
public class QmCode {
	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;
	
	/**
	 * 失败
	 */
	public static final int DEFEATED = 2;
	
	/**
	 * 参数提供不完整
	 */
	public static final int PARAM_NOT = 100;
	
	/**
	 * 参数错误
	 */
	public static final int PARAM_ERROR = 101;
	
	/**
	 * 服务器未知错误
	 */
	public static final int UNKNOWN_ERROR = 200;
	
	/**
	 * 版本号未通过
	 */
	public static final int NOT_VERSION = 301;
	
	/**
	 * 未登录
	 */
	public static final int NOT_LOGIN_STATUS = 306;
	
	/**
	 * 登录错误
	 */
	public static final int LOGIN_ERROR = 307;
	
	/**
	 * 服务器错误
	 */
	public static final int SERVER_ERROR = 500;
	
	/**
	 * 根据code获取对应msg
	 * @param code
	 * @return
	 */
	public static final String getMsg(int code) {
		String msg;
		switch(code) {
		case 1:
			msg = "Success";
			break;
		case 2:
			msg = "Defeated";
			break;
		case 100:
			msg = "Params Is Error";
			break;
		case 200:
			msg = "Unknown Server Error";
			break;
		case 301:
			msg = "Not Version";
			break;
		case 306:
			msg = "Not Login Status";
			break;
		case 307:
			msg = "Login Error";
			break;
		case 500:
			msg = "Server Error";
			break;
			default:
				msg = "Unknown Msg";
				break;
		}
		
		return msg;
	}
}
