package com.ratelimiter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ratelimiter.model.RateLimitRule;
import com.ratelimiter.service.RuleUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RuleController {

    private final RuleUpdateService service;

    @PutMapping("/update")
    public ResponseEntity<RateLimitRule> update(@RequestBody RateLimitRule rule) {
        return ResponseEntity.ok(service.updateRule(rule));
    }
}
