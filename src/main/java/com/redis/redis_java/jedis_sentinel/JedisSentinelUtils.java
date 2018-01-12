package com.redis.redis_java.jedis_sentinel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import org.springframework.beans.factory.annotation.Autowired;

import com.redis.redis_java.SerializeUtils;

public class JedisSentinelUtils {

	private static Logger logger = LoggerFactory.getLogger(JedisSentinelUtils.class);

	/** Redis 数据缓存时间*/
	private static final int DEFAULT_CACHE_SECONDS = 3600;

	/** Redis 连接池 (自动注入自于SpringMvc)*/
	@Autowired
	private static JedisSentinelPool jedisSentinelPool;
	
	/**
	 * 释放redis资源
	 * 
	 * @param jedis
	 */
	private static void releaseResource(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	/**
	 * 清空Redis
	 * 
	 * @param jedis
	 * @throws Exception
	 */
	public static void flushAll() {
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			jedis.flushAll();
		} catch (Exception e) {
			logger.error("Cache清空失败：" + e);
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 保存一个对象到Redis
	 * 
	 * @param key
	 * @param object
	 * @return true or false
	 * @throws Exception
	 */
	public static Boolean save(Object key, Object object) {logger.error("-------------：{}", jedisSentinelPool);
		return save(key, object, DEFAULT_CACHE_SECONDS);
	}

	/**
	 * 保存一个对象到redis,指定过期时间
	 * 
	 * @param key
	 * @param object
	 * @param seconds
	 * @return true or false
	 */
	public static Boolean save(Object key, Object object, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(object));
			jedis.expire(SerializeUtils.serialize(key), seconds);
			return true;
		} catch (Exception e) {
			logger.error("Cache保存失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 根据缓存键获取Redis缓存中的值
	 * 
	 * @param key
	 * @return Object
	 * @throws Exception
	 */
	public static Object get(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			byte[] obj = jedis.get(SerializeUtils.serialize(key));
			return obj == null ? null : SerializeUtils.unSerialize(obj);
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 根据缓存键清除Redis缓存中的值
	 * 
	 * @param key
	 * @return true or false
	 * @throws Exception
	 */
	public static Boolean del(Object key) {
		Jedis jedis = null;
		try {
			// System.out.println(key);
			jedis = jedisSentinelPool.getResource();
			jedis.del(SerializeUtils.serialize(key));
			return true;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 根据缓存键清除Redis缓存中的值
	 * 
	 * @param keys
	 * @return true or false
	 * @throws Exception
	 */
	public static Boolean del(Object... keys) {
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			jedis.del(SerializeUtils.serialize(keys));
			return true;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * @param key
	 * @param seconds
	 * @return
	 */
	public static Boolean expire(Object key, int seconds) {

		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			jedis.expire(SerializeUtils.serialize(key), seconds);
			return true;
		} catch (Exception e) {
			logger.error("Cache设置超时时间失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 添加一个内容到指定key的hash中
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return true or false
	 */
	public static Boolean addHash(String key, Object field, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			jedis.hset(SerializeUtils.serialize(key), SerializeUtils.serialize(field), SerializeUtils.serialize(value));
			return true;
		} catch (Exception e) {
			logger.error("Cache保存失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 从指定hash中拿一个对象
	 * 
	 * @param key
	 * @param field
	 * @return Object
	 */
	public static Object getHash(Object key, Object field) {
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			byte[] obj = jedis.hget(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
			return SerializeUtils.unSerialize(obj);
		} catch (Exception e) {
			logger.error("Cache读取失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 从hash中删除指定filed的值
	 * 
	 * @param key
	 * @param field
	 * @return true or false
	 */
	public static Boolean delHash(Object key, Object field) {
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			long result = jedis.hdel(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
			return result == 1 ? true : false;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 拿到缓存中所有符合pattern的key
	 * 
	 * @param pattern
	 * @return Set<byte[]>
	 */
	public static Set<byte[]> keys(String pattern) {
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			Set<byte[]> allKey = jedis.keys(("*" + pattern + "*").getBytes());
			return allKey;
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return new HashSet<byte[]>();
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 获得hash中的所有key value
	 * 
	 * @param key
	 * @return Map<byte[], byte[]>
	 */
	public static Map<byte[], byte[]> getAllHash(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			Map<byte[], byte[]> map = jedis.hgetAll(SerializeUtils.serialize(key));
			return map;
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 判断一个key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public static Boolean exists(Object key) {
		Jedis jedis = null;
		Boolean result = false;
		try {
			jedis = jedisSentinelPool.getResource();
			result = jedis.exists(SerializeUtils.serialize(key));
			return result;
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}
	
	/**
	 * 从main函数获取Spring容器内的bean实例
	 * 
	 * @param jedisSentinelPool
	 * */
	public static void setJedisSentinelPool(JedisSentinelPool _jedisSentinelPool) {
		jedisSentinelPool = _jedisSentinelPool;
	}

	public static JedisSentinelPool getJedisSentinelPool() {
		return jedisSentinelPool;
	}
}