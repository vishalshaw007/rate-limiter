package com.ratelimiter.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rate_limit_rules")
@Data
public class RateLimitRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    private String api;
    private String algorithm;

    private int limitCount;
    private int windowSize;

    private int capacity;
    private double refillRate;
    private double leakRate;

    // getters and setters
}