package com.ratelimiter.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ratelimiter.model.RateLimitRule;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RulePublisher {
    @Autowired private KafkaTemplate<String, String> kafka;

    public void publish(RateLimitRule r) {
        String msg = r.getClientId() + ":" + r.getApi();
        kafka.send("rate-limit-topic", msg);
        log.info("Published rule update {}", msg);
    }
}
