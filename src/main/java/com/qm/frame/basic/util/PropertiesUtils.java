package com.qm.frame.basic.util;

import com.qm.frame.basic.config.QmFrameConcent;
import com.qm.frame.basic.exception.QmFrameException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Copyright © 2018浅梦工作室. All rights reserved.
 * @author 浅梦
 * @date 2018年11月24日 上午2:09:11
 * @Description properties工具类，默认读取config.properties配置文件
 */
public class PropertiesUtils {

	private PropertiesUtils() {}

	private static Properties getProperties(String fileName){
		try {
			Properties properties = new Properties();
			// 读取properties文件,使用InputStreamReader字符流防止文件中出现中文导致乱码
			InputStreamReader inStream = new InputStreamReader
					(QmFrameConcent.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
			properties.load(inStream);
			inStream.close();
			return properties;
		} catch (Exception e) {
			throw new RuntimeException("读取[" + fileName + "]发生了异常！",e);
		}
	}

	/**
	 * 获取值
	 * @param fileName 文件名
	 * @param key 键
	 * @return
	 */
	public static String get(String fileName,String key){
		Properties properties = getProperties(fileName);
		String value = properties.getProperty(key);
		return value;
	}

	/**
	 * 获取值
	 * @param fileName 文件名
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String get(String fileName,String key,String defaultValue){
		Properties properties = getProperties(fileName);
		String value = properties.getProperty(key,defaultValue);
		return value;
	}
}