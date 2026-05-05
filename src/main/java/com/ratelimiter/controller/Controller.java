package com.ratelimiter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ratelimiter.service.RateLimiterService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class Controller {

    @Autowired private RateLimiterService service;

    @GetMapping("/test")
    public ResponseEntity<String> test(
            @RequestHeader(name = "clientId", required = true) String clientId) {

        if (!service.allow(clientId, "/test")) {
            log.warn("Rate limit exceeded for {}", clientId);
            return ResponseEntity.status(429).body("Too many requests");
        }

        return ResponseEntity.ok("Success");
    }
}
