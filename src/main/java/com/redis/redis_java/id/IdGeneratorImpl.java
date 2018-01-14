package com.redis.redis_java.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("idGenerator")
public class IdGeneratorImpl implements IdGenerator {
	private static final Logger logger = LoggerFactory.getLogger(IdGeneratorImpl.class);

	private static IdGeneratorUtil idGenerator = null;

	static {
		idGenerator = IdGeneratorUtil.builder()
				.addHost("192.168.2.126", 6379, "pw123", "c5809078fa6d652e0b0232d552a9d06d37fe819c")
//				.addHost(ip, port, password, luasha)
		.build();
	}

	@Override
	public Long getId(String tab, long shardId) {
		try {
			long id = idGenerator.next(tab, shardId);
			return id;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Long getOrderId() {
		return getId("order", 123456789);
	}

	@Override
	public Long getRecordId() {
		return getId("record", 123456789);
	}
}