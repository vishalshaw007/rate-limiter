package com.ratelimiter.limiter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import com.ratelimiter.model.RateLimitRule;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenLimiter implements RateLimiter {
    @Autowired private RedisTemplate<String, String> rt;
    @Autowired private DefaultRedisScript<Long> tokenScript;

    public boolean allow(String key, RateLimitRule r) {
    	Long res = rt.execute(
    	        tokenScript,
    	        Collections.singletonList(key),
    	        String.valueOf(r.getCapacity()),
    	        String.valueOf(r.getRefillRate()),
    	        String.valueOf(System.currentTimeMillis())
    	);
        log.info("TOKEN key={} result={}", key, res);
        return res == 1;
    }
}