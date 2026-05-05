package com.ratelimiter.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ratelimiter.service.RuleService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RuleConsumer {

    @Autowired private RuleService service;

    @KafkaListener(topics = "rate-limit-topic")
    public void consume(String msg) {
        String[] parts = msg.split(":");
        service.invalidate(parts[0], parts[1]);
        log.info("Cache invalidated for {}", msg);
    }
}

