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
public class SlidingLogLimiter implements RateLimiter {
    @Autowired private RedisTemplate<String, String> rt;
    @Autowired private DefaultRedisScript<Long> slidingLogScript;

    public boolean allow(String key, RateLimitRule r) {
    	Long res = rt.execute(
    	        slidingLogScript,
    	        Collections.singletonList(key),
    	        String.valueOf(r.getLimitCount()),
    	        String.valueOf(r.getWindowSize() * 1000L),
    	        String.valueOf(System.currentTimeMillis())
    	);
        log.info("SLIDING key={} result={}", key, res);
        return res == 1;
    }
}
