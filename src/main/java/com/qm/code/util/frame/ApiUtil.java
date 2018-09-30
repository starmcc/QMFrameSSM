package com.qm.code.util.frame;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qm.code.util.des.DES3Util;
import com.qm.code.util.io.PropertiesUtil;
import com.qm.code.util.logger.QmLog;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月20日 上午12:57:17
 * @Description 数据请求、返回工具类
 */
public class ApiUtil {
	//调用系统全局配置
	
	private ApiUtil() {};
	
	/**
	 * isEndNullObjectStringValue需要调用的静态变量
	 */
	public static String errorMsg = "";
	/**
	 * 参数错误时的后缀模板
	 */
	private static final String ERROR_MSG_TEMPLATE = "参数错误";
	//返回code规范常量调用
	/**
	 * 成功
	 */
	public static final int CODE_SUCCESS = 1;
	
	/**
	 * 失败
	 */
	public static final int CODE_DEFEATED = 2;
	
	/**
	 * 参数错误
	 */
	public static final int CODE_PARAM_ERROR = 100;
	
	/**
	 * 未知错误
	 */
	public static final int CODE_UNKNOWN_ERROR = 200;
	
	/**
	 * 未登录
	 */
	public static final int CODE_LOGIN_NOT = 306;
	
	/**
	 * 登录错误
	 */
	public static final int CODE_LOGIN_ERROR = 307;
	
	/**
	 * 服务器错误
	 */
	public static final int CODE_SERVER_ERROR = 500;
	//返回code规范常量调用
	
	/**
	 * 请求解析
	 * 解析格式为{"value":{"param":"xxx","param2":"xxx"}}的JSON参数
	 * @param value 前端传入的数据
	 * @return
	 */
	public static Map<String,Object> getJsonValueDesMap(String value){
    	String str = "";
    	Map<String,Object> map = null;
    	if(PropertiesUtil.get("DES3.start").trim().equals("true")) {
    		try {
    			//解密
				str = DES3Util.decode(value);
				map = JSON.parseObject(str);
				//解析value对象
				String strTemp = map.get("value").toString();
				map = JSON.parseObject(strTemp);
			} catch (Exception e) {
				e.printStackTrace();
				QmLog.debug("解密失败");
			}
    	}else {
    		//不解密
    		map = JSON.parseObject(value);
    		String strTemp = map.get("value").toString();
			map = JSON.parseObject(strTemp);
    	}
    	return map;
    }
	
	
	/**
	 * 请求解析(重载)
	 * 解析格式为{"value":{"param":"xxx","param2":"xxx"}}的JSON参数
	 * @param value 前端传入的数据
	 * @param desType 是否解密
	 * @return
	 */
	public static Map<String,Object> getJsonValueDesMap(String value,boolean desType){
    	String str = "";
    	Map<String,Object> map = null;
    	//全局控制，目前为不加密状态
    	if(PropertiesUtil.get("DES3.start").trim().equals("true") && desType) {
    		try {
    			//解密
				str = DES3Util.decode(value);
				map = JSON.parseObject(str);
				//解析value对象
				String strTemp = map.get("value").toString();
				map = JSON.parseObject(strTemp);
			} catch (Exception e) {
				e.printStackTrace();
				QmLog.debug("解密失败");
			}
    	}else {
    		//不解密
    		map = JSON.parseObject(value);
    		String strTemp = map.get("value").toString();
			map = JSON.parseObject(strTemp);
    	}
    	return map;
    }
	
	/**
	 * 接口回调方法
	 * @param code 回调编码
	 * @param
	 * @param data 回调数据
	 * @return
	 */
	public static String sendJSON(int code,String msg,Object data) {
		//示例
		//ResponseDataUtil.sendJSON(1,"信息", "obj数据");
		Map<String,Object> responseMap = new HashMap<String,Object>();
		responseMap.put("code", code);
		responseMap.put("msg", msg);
		responseMap.put("data", data);
		Map<String,Object> responseMap2 = new HashMap<String,Object>();
		//SerializerFeature.WriteMapNullValue设置后,返回Bean时字段为空时默认返回null
		String value = JSON.toJSONString(responseMap,SerializerFeature.WriteMapNullValue);
		try {
			if(PropertiesUtil.get("DES3.start").trim().equals("true")) {
				value = DES3Util.encode(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			QmLog.debug("加密失败");
		}
		responseMap2.put("value", value);
		return JSON.toJSONString(responseMap2);
	}
	
	
	/**
	 * 判断对象中字段值是否存在null，如果有null返回false,调用此类中的errorMsg可以查看哪个字段为null。
	 * 注意，这里只能检测可以转成String类型的Object
	 * @param object 操作对象
	 * @param fieldNames 字段名
	 * @return
	 * @throws Exception
	 */
	public static boolean IsEndNullObjectValue(Object object,String... fieldNames) throws Exception{
		Class<?> classTemp = object.getClass();
		Field[] field = classTemp.getDeclaredFields();
		for (Field field2 : field) {
			field2.setAccessible(true);
			Object tempObj = field2.get(object);
			for (int i = 0; i < fieldNames.length; i++) {
				if(field2.getName().equals(fieldNames[i])) {
					if(tempObj == null || tempObj.toString().trim().equals("")) {
						errorMsg = field2.getName() + ERROR_MSG_TEMPLATE;
						return false;
					}
				}
			}
		}
		errorMsg = "IsEndNullObjectValue ok";
		return true;
	}
	
	
	
	/**
	 * 判断Map中是否存在指定key和value是否为空，为空返回false。这里为空包括空字符串
	 * 返回false时,该工具类的errorMsg信息会更新为开key值
	 * @param map 操作map
	 * @param keys key
	 * @return
	 * @throws Exception
	 */
	public static boolean IsEndNullMap(Map<String,?> map,String... keys){
		if(map != null) {
			boolean is;
			for (String key : keys) {//找key
				is = false;
				int i = 0;
				//遍历map所有的key来做对比
				for (Map.Entry<String,?> entry : map.entrySet()) {
					i++;
					if(entry.getKey().equals(key)){//找key对应
						//找对应的value判断是否为空
				    	if(entry.getValue() == null || entry.getValue().toString().trim().equals("")) {
				    		errorMsg = key;
				    		return false;
				    	}
				    	is = true;
						break;
					}
					if(i >= map.size() && !is) {//如果到最大值后,没有跳出循环,就找不到该key了
						errorMsg = key;
						return false;
					}
				}
			}
		}else {
			errorMsg = "检测不到传递数据";
			return false;
		}
		errorMsg = "requestMapIsEndNull ok";
		return true;
	}
	
	/**
	 * 获取并转化JSON对象
	 * @param JsonStr
	 * @param className
	 * @return
	 */
	public static <T> T getJsonToBean(String jsonStr,Class<T> clamm) {
		T obj;
		if(jsonStr != null) {
			try {
				obj = JSON.parseObject(jsonStr,clamm);
			} catch (Exception e1) {
				obj = null;
			}
		}else {
			obj = null;
		}
		return obj;
	}
	
	/**
	 * 获取并转化JSON数组为List
	 * @param jsonStr
	 * @return
	 */
	public static <T> List<T> getJsonToList(String jsonStr,Class<T> clamm){
		if(jsonStr == null || jsonStr.trim().equals("")) {
			return null;
		}
		JSONArray jsonArray = JSONArray.parseArray(jsonStr);
		List<T> lis = jsonArray.toJavaList(clamm);
		return lis;
	}
	


	/**
	 * QM浅梦-类对象反射赋值封装方法
	 * 指定实体类对象中的所有类型为type的字段，当值为value时,设置为newValue.
	 * @param obj 将要操作的对象
	 * @param fieldType 将要操作的对象中存在字段的类型名
	 * @param value	需要替换的值
	 * @param newValue 替换后的值
	 * @return
	 * @throws Exception
	 */
	public static <T> T changeEntityField(T obj,String fieldType,Object value,Object newValue) throws Exception{
		//获取object对象的类字节码
		Class<?> classTemp = obj.getClass();
		//根据类字节码获取字段列表field
		Field[] fields = classTemp.getDeclaredFields();
		//遍历field数组，访问和操作每个字段
		String fieldTypeTemp = null;
		for (Field field : fields) {
			//如果字段是私有的，必须设置为true才能进行访问。
			field.setAccessible(true);  
			//获取该字段的类型
			fieldTypeTemp = field.getType().toString();
			int lastIndex = fieldTypeTemp.lastIndexOf(".");  
			fieldTypeTemp = fieldTypeTemp.substring(lastIndex + 1);
			//判断是这个类型的情况下进行操作
			if(fieldTypeTemp.equals(fieldType)) {
				//获取当前字段的值
				Object obj2 = field.get(obj);
				//如果当前字段的值为null且需要替换的值也为null时
				if(obj2 == null && value == null) {
					field.set(obj, newValue);
				}else {
					//当前字段的值是否等于需要替换的值
					if(obj2 != null && value != null && obj2.equals(value)) {
						//等于的情况下对这个字段进行set操作
						field.set(obj, newValue);
					}
				}
			}
		}
		return obj;
	}
	
	/**
	 * QM-浅梦 过滤对象或实体类中指定的字段,并转化为map对象
	 * 在实际中，返回数据到前端时，直接传对象会造成多余数据，用此方法减少多余字段。
	 * @param obj 需要操作的对象
	 * @param fieldName 多个参数列表,需要过滤掉的字段名
	 * @return
	 * @throws Exception
	 */

	public static<T> Map<String,Object> lessenObjectToMap(T obj,String... fieldNames) throws Exception{
		//获取object对象的类字节码
		Class<?> classTemp = obj.getClass();
		//根据类字节码获取字段列表field
		Field[] fields = classTemp.getDeclaredFields();
		//遍历field数组，访问和操作每个字段
		boolean isEnd = false;
		Map<String,Object> map = new HashMap<String,Object>();
		for (Field field : fields) {
			//如果字段是私有的，必须设置为true才能进行访问。
			field.setAccessible(true);
			isEnd = false;
			for (int i = 0; i < fieldNames.length; i++) {
				//获取该字段名进行对fieldName的循环匹配,如果匹配正确跳出循环
				if(field.getName().equals(fieldNames[i])) {
					isEnd = true;
					break;
				}
			}
			//如果这个字段是找不到的,那么就是说这个字段需要传入。
			if(!isEnd) {
				map.put(field.getName(), field.get(obj));
			}
		}
		return map;
	}
	
	/**
	 * 正常MD5加密
	 * @param str
	 * @return
	 */
	private static String md5Hex(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(str.getBytes());
			return new String(new Hex().encode(digest));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			return "";
		}
	}

	/**
	 * MD5加密+盐
	 * @param password
	 * @return
	 */
	public static String getSaltMD5(String password) {
		// 生成一个16位的随机数
		Random random = new Random();
		StringBuilder sBuilder = new StringBuilder(16);
		sBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
		int len = sBuilder.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sBuilder.append("0");
			}
		}
		// 生成最终的加密盐
		String Salt = sBuilder.toString();
		password = md5Hex(password + Salt);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = password.charAt(i / 3 * 2);
			char c = Salt.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = password.charAt(i / 3 * 2 + 1);
		}
		return String.valueOf(cs);
	}

	/**
	 * 校验MD5+盐密
	 * @param password
	 * @param md5str
	 * @return
	 */
	public static boolean getVerifyMD5(String password, String md5str) {
		char[] cs1 = new char[32];
		char[] cs2 = new char[16];
		for (int i = 0; i < 48; i += 3) {
			cs1[i / 3 * 2] = md5str.charAt(i);
			cs1[i / 3 * 2 + 1] = md5str.charAt(i + 2);
			cs2[i / 3] = md5str.charAt(i + 1);
		}
		String Salt = new String(cs2);
		return md5Hex(password + Salt).equals(String.valueOf(cs1));
	}
	
	/**
	 * MD5测试用例
	 * @param args
	 */
	public static void main(String[] args) {
		String value = getSaltMD5("test");
		System.out.println(value);
		boolean is = getVerifyMD5("test",value);
		System.out.println(is);
	}
	
	  /**
     * 根据属性名获取属性值
     * 
     * @param fieldName
     * @param object
     * @return
     */
    public static Object getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        } 
    }

}
