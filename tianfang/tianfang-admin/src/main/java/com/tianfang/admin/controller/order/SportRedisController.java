package com.tianfang.admin.controller.order;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.tianfang.common.redis.RedisService;

/**
 * 
 * @author Administrator
 * @param <T>
 *
 */
@Controller
public class SportRedisController<T> {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * redis 添加
	 * @param spec
	 */
	public void addRedis(List<T> lis){
		if(lis.size()>0){
			String key = lis.get(0).getClass().getSimpleName();
			redisTemplate.opsForValue().set(key, lis);
		}
	}
}
