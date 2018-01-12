package com.redis.redis_java.jedis.properties;

import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyConfigurer extends PropertyPlaceholderConfigurer {

    private Properties properties;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
                            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        properties = props;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public String getProperty(String key, String value) {
        return properties.getProperty(key, value);
    }

    public Object setProperty(String key, String value) {
        return properties.setProperty(key, value);
    }
}