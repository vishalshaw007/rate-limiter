package com.ratelimiter.limiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterFactory {
    @Autowired FixedLimiter fixed;
    @Autowired SlidingLogLimiter sliding;
    @Autowired TokenLimiter token;
    @Autowired LeakyLimiter leaky;

    public RateLimiter get(String algo) {
        switch (algo) {
            case "FIXED":
                return fixed;
            case "SLIDING_LOG":
                return sliding;
            case "TOKEN":
                return token;
            case "LEAKY":
                return leaky;
            default:
                throw new RuntimeException("Unsupported");
        }
    }
}
