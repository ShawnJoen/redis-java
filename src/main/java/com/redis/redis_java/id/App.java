package com.redis.redis_java.id;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	private static ClassPathXmlApplicationContext context;
	
	public static void main(String[] args) {
		
    	//context = new ClassPathXmlApplicationContext(new String[] {"spring-context.xml"}); 
    	//IdGenerator idGenerator = (IdGenerator) context.getBean("idGenerator");
		IdGenerator idGenerator = new IdGeneratorImpl();
				
    	/**
    	 * 需要 redis server版本是3.2以上，因为使用到了redis.replicate_commands()
    	 * 需要 关闭服务器上的ntp等时间同步机制
    	 * 需要配置 wget https://raw.githubusercontent.com/hengyunabc/redis-id-generator/master/redis-script-node1.lua
		 * 		./redis-cli script load "$(cat redis-script-node1.lua)"
    	 */
		long orderId = idGenerator.getOrderId();
    	System.out.println(orderId);//return 19位整数
    	System.out.println(idGenerator.getRecordId());//return 19位整数

    	List<Long> result = IdGeneratorUtil.parseId(orderId);
		System.out.println("miliSeconds:" + result.get(0) + ", partition:"
				+ result.get(1) + ", seq:" + result.get(2));
		
		for (int i = 0;i < 100 ;i++) {
			System.out.println(idGenerator.getId("test", i));
		}
	}
}