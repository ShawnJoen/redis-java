package com.redis.redis_java.jedis.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("propertiesServiceImpl")
public class PropertiesServiceImpl implements PropertiesService {
	
	@Autowired
    private PropertyConfigurer propertyConfigurer;
	
	@Override
    public String getProperty(String key) {
        return propertyConfigurer.getProperty(key);
    }

	@Override
    public String getProperty(String key, String value) {
        return propertyConfigurer.getProperty(key, value);
    }
}