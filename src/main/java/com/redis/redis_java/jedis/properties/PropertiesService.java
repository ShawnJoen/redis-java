package com.redis.redis_java.jedis.properties;

public interface PropertiesService {
    String getProperty(String key);
    String getProperty(String key, String value);
}