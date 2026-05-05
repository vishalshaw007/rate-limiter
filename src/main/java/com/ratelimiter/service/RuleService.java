package com.ratelimiter.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ratelimiter.model.RateLimitRule;
import com.ratelimiter.repository.RuleRepository;
import com.ratelimiter.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RuleService {
    @Autowired private RedisTemplate<String, String> redisTemplate;
    @Autowired private RuleRepository repo;

	public RateLimitRule get(String client, String api) {
        String key = "rule:" + client + ":" + api;
        String json = redisTemplate.opsForValue().get(key);

        if (json != null) {
            log.info("Cache HIT {}", key);
            return JsonUtil.fromJson(json, RateLimitRule.class); // ✅ FIX
        }

        log.info("Cache MISS {}", key);
        RateLimitRule r = repo.findByClientIdAndApi(client, api);

        if (r != null) {
        	redisTemplate.opsForValue().set(key, JsonUtil.toJson(r),60,TimeUnit.SECONDS);
        }

        return r;
    }

    public void invalidate(String client, String api) {
    	redisTemplate.delete("rule:" + client + ":" + api);
    }
}
