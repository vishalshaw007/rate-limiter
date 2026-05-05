package com.ratelimiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisConfig {

    @Bean
    public DefaultRedisScript<Long> fixedScript() {
        return script("lua/fixed.lua");
    }

    @Bean
    public DefaultRedisScript<Long> slidingLogScript() {
        return script("lua/sliding_log.lua");
    }

    @Bean
    public DefaultRedisScript<Long> tokenScript() {
        return script("lua/token_bucket.lua");
    }

    @Bean
    public DefaultRedisScript<Long> leakyScript() {
        return script("lua/leaky_bucket.lua");
    }

    private DefaultRedisScript<Long> script(String path) {
        DefaultRedisScript<Long> s = new DefaultRedisScript<Long>();
        s.setLocation(new ClassPathResource(path));
        s.setResultType(Long.class);
        return s;
    }
}
