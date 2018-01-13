package com.redis.redis_java.jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;

public class App {

	public static void main(String[] args) {
		
    	String r1 = JedisUtils.getInstance().strings().set("k1", "123");//return OK
    	String r2 = JedisUtils.getInstance().strings().get("k1");//return 有值输出值, null
    	
    	Map<String, Object> m1 = new HashMap();
    	m1.put("name", "shawn");
    	m1.put("age", 33);
    	JedisUtils.getInstance().strings().set("k2", JSON.toJSONString(m1));
    	String r3 = JedisUtils.getInstance().strings().get("k2");//{"name":"shawn","age":33}, null
    	
    	//long l1 = JedisUtils.getInstance().strings().append("k1", "123");//return 6
    	//System.out.println(r2);//print 123
    	//System.out.println(JedisUtils.getInstance().strings().get("k1"));//print 123123
    	
    	if (JedisUtils.getInstance().keys().exists("k1")) {//is true
    		System.out.println("true");
    	} else {
    		System.out.println("false");
    	}
    	
    	//JedisUtils.getInstance().keys().flushAll();//clear all databases
    	
    	//long r4 = JedisUtils.getInstance().strings().incrBy("n1", 1);//return ++1
    	//long r5 = JedisUtils.getInstance().strings().decrBy("n1", 2);//return --2
    	
    	long r6 = JedisUtils.getInstance().strings().strlen("k1");//return 6
    	
    	//long r7 = JedisUtils.getInstance().keys().expire("k1", 8);//28 is seconds, return 1
    	//long r8 = JedisUtils.getInstance().keys().ttl("k1");//return -1(永久), return 有设置过期时间会返回 剩余秒数,-2过期

    	long r9 = JedisUtils.getInstance().keys().del("k1", "k2", "k44");//return 2, 只返回成功删除的元素个数

    	Map<String, String> m2 = new HashMap();
    	m2.put("name", "shawn");
    	m2.put("gender", "male");
    	m2.put("age", "33");
    	
    	String r10 = JedisUtils.getInstance().hash().hmset("k3", m2);//return OK
    	
    	List<String> r11 = JedisUtils.getInstance().hash().hmget("k3", "gender", "age");//return [male, 33]
    	
    	long r12 = JedisUtils.getInstance().hash().hdel("k3", "gender");//return 1

    	System.out.println(JedisUtils.getInstance().hash().hmget("k3", "gender", "age"));//return [null, 33]
	}
}