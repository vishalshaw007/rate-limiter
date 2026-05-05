package com.ratelimiter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ratelimiter.limiter.RateLimiterFactory;
import com.ratelimiter.model.RateLimitRule;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RateLimiterService {

    @Autowired private RuleService ruleService;
    @Autowired private RateLimiterFactory factory;

    public boolean allow(String client, String api) {
        RateLimitRule r = ruleService.get(client, api);
        String key = "rate:" + client + ":" + api;

        boolean allowed = factory.get(r.getAlgorithm()).allow(key, r);

        log.info("client={} api={} allowed={}", client, api, allowed);
        return allowed;
    }
}
