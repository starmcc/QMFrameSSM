package com.qm.frame.redis;

import com.alibaba.fastjson.JSONObject;
import com.qm.frame.basic.config.QmFrameContent;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月20日 上午12:55:15
 * @Description 调用本类可直接调用方法 无需重新获取连接和关闭连接。 依赖redis.properties
 */
public class RedisUtils {
	private static final Logger LOG = Logger.getLogger(RedisUtils.class);
	private static Properties PRO = getProperties();
	// ip
	private static final String URL = PRO.getProperty("redis.url","127.0.0.1");
	// 端口
	private static final Integer PORT = Integer.parseInt(PRO.getProperty("redis.port","6379"));
	// 密码(原始默认是没有密码)
	private static final String PASSWORD = PRO.getProperty("redis.password","");
	// 最大连接数
	private static final Integer MAX_ACTIVE = Integer.parseInt(PRO.getProperty("redis.maxActive","1024"));
	// 设置最大空闲数
	private static final Integer MAX_IDLE = Integer.parseInt(PRO.getProperty("redis.maxIdle","200"));
	// 最大连接时间
	private static final Integer MAX_WAIT = Integer.parseInt(PRO.getProperty("redis.maxWait","10000"));
	// 超时时间
	private static final Integer TIME_OUT = Integer.parseInt(PRO.getProperty("redis.timeOut","10000"));
	// 在borrow一个事例时是否提前进行validate操作
	private static final boolean BORROW = Boolean.parseBoolean(PRO.getProperty("redis.borrow","true"));
	private static JedisPool pool = null;


	private final static Properties getProperties(){
		try {
			Properties properties = new Properties();
			// 读取properties文件,使用InputStreamReader字符流防止文件中出现中文导致乱码
			InputStreamReader inStream = new InputStreamReader
					(QmFrameContent.class.getClassLoader().getResourceAsStream("base.properties"),"UTF-8");
			properties.load(inStream);
			inStream.close();
			return properties;
		} catch (Exception e) {
			throw new RuntimeException("读取base.properties发生了异常！",e);
		}
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
			LOG.info("连接池连接异常");
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
			LOG.debug("设置失效失败.");
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
			LOG.debug("插入数据有异常.");
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
			LOG.debug("插入数据有异常.");
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
			LOG.debug("插入数据有异常.");
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
			LOG.debug("插入数据有异常.");
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
			LOG.debug("获取数据有异常.");
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
			LOG.debug("删除key异常.");
			return false;
		} finally {
			getColse(jedis);
		}
		return false;
	}
}