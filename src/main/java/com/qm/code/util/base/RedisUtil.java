package com.qm.code.util.base;

import com.alibaba.fastjson.JSONObject;
import com.qm.code.util.io.PropertiesUtil;
import com.qm.code.util.logger.QmLog;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月20日 上午12:55:15
 * @Description 调用本类可直接调用方法 无需重新获取连接和关闭连接。 依赖redis.properties
 */
public class RedisUtil {
	private static final String URL = PropertiesUtil.get("url"); // ip
	private static final Integer PORT = Integer.parseInt(PropertiesUtil.get("redis.port")); // 端口
	private static final String PASSWORD = PropertiesUtil.get("redis.password"); // 密码(原始默认是没有密码)
	private static final Integer MAX_ACTIVE = Integer.parseInt(PropertiesUtil.get("redis.maxActive")); // 最大连接数
	private static final Integer MAX_IDLE = Integer.parseInt(PropertiesUtil.get("redis.maxIdle")); // 设置最大空闲数
	private static final Integer MAX_WAIT = Integer.parseInt(PropertiesUtil.get("redis.maxWait")); // 最大连接时间
	private static final Integer TIME_OUT = Integer.parseInt(PropertiesUtil.get("redis.timeOut")); // 超时时间
	private static final boolean BORROW = Boolean.parseBoolean(PropertiesUtil.get("redis.borrow")); // 在borrow一个事例时是否提前进行validate操作
	private static JedisPool pool = null;

	
	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// RedisUtils.addValue("aaa", "aaa");
		RedisUtil.addValue("1", "test");
		RedisUtil.delKey("1");
		System.out.println(RedisUtil.get("1"));

	}

	/**
	 * 初始化线程池
	 */
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(BORROW);
		pool = new JedisPool(config, URL, PORT, TIME_OUT, PASSWORD);
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	private static synchronized Jedis getJedis() {
		try {
			if (pool != null) {
				return pool.getResource();
			} else {
				return null;
			}
		} catch (Exception e) {
			QmLog.info("连接池连接异常");
			return null;
		}

	}

	/**
	 * 关闭连接
	 * 
	 * @param jedis
	 */
	private static void getColse(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 设置失效时间
	 * 
	 * @param key
	 *            键
	 * @param seconds
	 *            时间(秒)
	 */
	public static void disableTime(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.auth(PASSWORD);
			jedis.expire(key, seconds);

		} catch (Exception e) {
			QmLog.debug("设置失效失败.");
		} finally {
			getColse(jedis);
		}
	}

	/**
	 * 保存对象
	 * 
	 * @param key
	 *            键
	 * @param obj
	 *            对象
	 * @return
	 */
	public static boolean addObject(String key, Object obj) {

		Jedis jedis = null;
		String value = JSONObject.toJSONString(obj);
		try {
			jedis = getJedis();
			jedis.auth(PASSWORD);
			String code = jedis.set(key, value);
			if (code.equals("ok")) {
				return true;
			}
		} catch (Exception e) {
			QmLog.debug("插入数据有异常.");
			return false;
		} finally {
			getColse(jedis);
		}
		return false;
	}

	/**
	 * 保存对象
	 * 
	 * @param key
	 *            键
	 * @param obj
	 *            对象
	 * @param seconds
	 *            失效时间
	 * @return
	 */
	public static boolean addObject(String key, Object obj, int seconds) {

		Jedis jedis = null;
		String value = JSONObject.toJSONString(obj);
		try {
			jedis = getJedis();
			jedis.auth(PASSWORD);
			String code = jedis.set(key, value);
			if (code.equals("ok")) {
				jedis.expire(key, seconds);
				return true;
			}
		} catch (Exception e) {
			QmLog.debug("插入数据有异常.");
			return false;
		} finally {
			getColse(jedis);
		}
		return false;
	}

	/**
	 * 保存字符串
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean addValue(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.auth(PASSWORD);
			String code = jedis.set(key, value);
			if (code.equals("ok")) {
				return true;
			}
		} catch (Exception e) {
			QmLog.debug("插入数据有异常.");
			return false;
		} finally {
			getColse(jedis);
		}
		return false;
	}

	/**
	 * 保存字符串
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param seconds
	 *            失效时间
	 * @return
	 */
	public static boolean addValue(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.auth(PASSWORD);
			String code = jedis.set(key, value);
			if (code.equals("ok")) {
				jedis.expire(key, seconds);
				return true;
			}
		} catch (Exception e) {
			QmLog.debug("插入数据有异常.");
			return false;
		} finally {
			getColse(jedis);
		}
		return false;
	}

	/**
	 * 根据key获取value
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static Object get(String key) {
		Jedis jedis = null;
		Object obj;
		try {
			jedis = getJedis();
			jedis.auth(PASSWORD);
			obj = jedis.get(key);
		} catch (Exception e) {
			QmLog.debug("获取数据有异常.");
			return null;
		} finally {
			getColse(jedis);
		}
		return obj;
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean delKey(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.auth(PASSWORD);
			Long code = jedis.del(key);
			if (code > 1) {
				return true;
			}
		} catch (Exception e) {
			QmLog.debug("删除key异常.");
			return false;
		} finally {
			getColse(jedis);
		}
		return false;
	}
}