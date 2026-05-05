package com.ratelimiter.service;

import org.springframework.stereotype.Service;

import com.ratelimiter.kafka.RulePublisher;
import com.ratelimiter.model.RateLimitRule;
import com.ratelimiter.repository.RuleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleUpdateService {

    private final RuleRepository repo;
    private final RulePublisher publisher;

    public RateLimitRule updateRule(RateLimitRule updatedRule) {

        // 1. Fetch existing rule
        RateLimitRule existing = repo
            .findByClientIdAndApi(updatedRule.getClientId(), updatedRule.getApi());

        if (existing == null) {
            throw new RuntimeException("Rule not found");
        }

        // 2. Update fields
        existing.setLimitCount(updatedRule.getLimitCount());
        existing.setWindowSize(updatedRule.getWindowSize());
        existing.setCapacity(updatedRule.getCapacity());
        existing.setRefillRate(updatedRule.getRefillRate());
        existing.setLeakRate(updatedRule.getLeakRate());
        existing.setAlgorithm(updatedRule.getAlgorithm());

        // 3. Save (this will UPDATE)
        RateLimitRule saved = repo.save(existing);

        // 4. Publish Kafka event
        publisher.publish(saved);

        return saved;
    }
}
