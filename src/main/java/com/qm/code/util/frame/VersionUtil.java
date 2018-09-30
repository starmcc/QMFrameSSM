package com.qm.code.util.frame;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.qm.code.util.io.PropertiesUtil;
import com.qm.code.util.logger.QmLog;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月20日 上午12:32:18
 * @Description 版本号验证工具
*/
public class VersionUtil {
	/**
	 * 自动验证版本号,依赖config.properties
	 * @param request 请求header中必须带有version字段
	 * @return
	 * @throws IOException
	 */
	public static boolean verify(HttpServletRequest request) throws IOException {
		if(PropertiesUtil.get("version.start").trim().equals("false")) {
			//不开启版本控制
			return true;
		}
		//目前版本号
		String versionRequest = request.getHeader("version");
		QmLog.debug("请求版本号：" + versionRequest);
		String versionNow = PropertiesUtil.get("version");
		QmLog.debug("当前版本号：" + versionNow);
		if(versionNow.equals(versionRequest)) {
			//通过
			return true;
		}
		QmLog.debug("进入版本控制判断");
		String[] versionPermit = PropertiesUtil.get("version.permit").split(",");
		if(versionPermit != null && versionPermit.length > 0) {
			for (String version : versionPermit) {
				if(version.equals(versionRequest)) {
					//通过
					return true;
				}
			}
		}else {
			QmLog.debug("请求失败,服务器并无配置可允许版本");
			return false;
		}
		QmLog.debug("错误失败返回");
		return false;
	}
	
    /**
     * 获取版本号
     * @param request
     * @return
     */
    public static String getVersion(HttpServletRequest request) {  
    	String version = request.getHeader("version");
        return version;  
    }  
}
