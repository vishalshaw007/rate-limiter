package com.ratelimiter.limiter;

import com.ratelimiter.model.RateLimitRule;

public interface RateLimiter {
    boolean allow(String key, RateLimitRule r);
}
