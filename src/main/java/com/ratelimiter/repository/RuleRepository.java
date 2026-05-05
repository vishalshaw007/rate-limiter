package com.ratelimiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ratelimiter.model.RateLimitRule;

@Repository
public interface RuleRepository extends JpaRepository<RateLimitRule, String> {
    RateLimitRule findByClientIdAndApi(String clientId, String api);
}
