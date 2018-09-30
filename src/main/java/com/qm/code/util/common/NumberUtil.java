package com.qm.code.util.common;

import java.util.Random;
import java.util.UUID;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月26日11:47:53
 * @Description 这是随机生成工具类
 */
public class NumberUtil {

	/**
	 * 生成UUID
	 * @return
	 */
	public static String createUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}
	
	/**
	 * 获取随机数字符串
	 * @param size 长度
	 * @return
	 */
	public static String createRandom(int size) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= size; i++) {
			sb.append(String.valueOf(random.nextInt(9)));
		}
		return sb.toString();
	}
	
	
}
