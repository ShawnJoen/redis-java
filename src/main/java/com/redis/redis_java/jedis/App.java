package com.redis.redis_java.jedis;

import java.util.HashMap;
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
    	String r3 = JedisUtils.getInstance().strings().get("k2");//{"name":"shawn","age":33}
    	
    	System.out.println(r3);
	}
}