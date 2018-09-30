package com.qm.code.util.io;

import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月20日 上午12:55:26
 * @Description properties工具
 */
public class PropertiesUtil {

	private static Properties properties;
	
	/**
	 * 如果需要读取其他配置文件请指定fileName的值;
	 */
	public static String fileName = null;
	
	private PropertiesUtil() {}
	
	static {
		properties = new Properties();
		InputStreamReader inStream;
		try {
			// 读取properties文件,使用InputStreamReader字符流防止文件中出现中文导致乱码
			if(fileName != null) {
				inStream = new InputStreamReader
						(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8");
			}else {
				inStream = new InputStreamReader
						(PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties"),"UTF-8");
			}
			properties.load(inStream);
			inStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 从properties文件中读取出一个key对应的value
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = properties.getProperty(key, null);
		return value;
	}
	
	/**
	 * 从properties文件中读取出一个key对应的value,如果该value为空返回默认文本
	 * @param key
	 * @param defaultValue
	 * @return
	 */
    public static String get(String key,String defaultValue){
    	String value= properties.getProperty(key);
    	if(StringUtils.isBlank(value)) {
    		value = defaultValue;
    	}
    	return value;    
	}

}
