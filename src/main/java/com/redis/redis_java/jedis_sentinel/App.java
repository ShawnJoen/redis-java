package com.redis.redis_java.jedis_sentinel;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.redis.redis_java.TestVO;

import redis.clients.jedis.JedisSentinelPool;

public class App {
	
	final static private String CUSTOM_REDIS_KEY = "customRedisKey2";
	
	private static ClassPathXmlApplicationContext context;
	
    public static void main( String[] args ) {
    	
    	context = new ClassPathXmlApplicationContext(new String[] {"spring-context.xml"}); 
    	//获取Spring容器内的 JedisSentinelPool实例
    	JedisSentinelPool jedisSentinelPool = (JedisSentinelPool) context.getBean("jedisSentinelPool");
     	JedisSentinelUtils.setJedisSentinelPool(jedisSentinelPool);
    	
    	TestVO testVO;
    	Object object = JedisSentinelUtils.get(CUSTOM_REDIS_KEY);
    	if (object == null) {
    		
    		testVO = new TestVO();
    		testVO.setName("測試 Redis 存儲數據");
    		JedisSentinelUtils.save(CUSTOM_REDIS_KEY, testVO);
    	} else {
    		testVO = (TestVO)object;
    	}
    	
    	System.out.println(testVO.getName());
    }
}