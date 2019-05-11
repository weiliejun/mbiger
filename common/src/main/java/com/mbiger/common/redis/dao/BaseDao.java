package com.mbiger.common.redis.dao;

import com.mbiger.common.redis.StringRedisTemplate;
import org.springframework.data.redis.core.RedisCommand;


public interface BaseDao {
	StringRedisTemplate getStringRedisTemplate(RedisCommand command);
}
