package com.yuanjun.weixindemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.util.JSONUtils;

@RestController
public class RedisController {

	@Resource(name="redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	private ValueOperations<String, String> strKey;
	private ZSetOperations<String, String> zset;
	
	@RequestMapping("addKey")
	public String addKey(@RequestParam("userkey")String userKey,@RequestParam("userMessage")String userMessage){
		long currentTime = System.currentTimeMillis()/1000;
		
		strKey =  redisTemplate.opsForValue();
		String uuid = UUID.randomUUID().toString();
		strKey.set(uuid, userMessage);
		
		 zset =  redisTemplate.opsForZSet();
		
		zset.add(userKey, uuid, currentTime);
		
		return "success";
	}
	
	@RequestMapping("queryBykey")
	public String queryByKey(@RequestParam("userkey")String userKey){
		
		zset =  redisTemplate.opsForZSet();
		
		Set<String> messageID =    zset.range(userKey, 0, Long.MAX_VALUE);
		strKey =  redisTemplate.opsForValue();
		List<String> messages = new ArrayList<String>();
		for(String mid:messageID){
			String message = strKey.get(mid);
			messages.add(message);
		}
		return JSONUtils.valueToString(messages);
	}
}
