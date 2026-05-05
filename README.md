## 🚀 Distributed Rate Limiter (Java + Redis + Kafka)

A production-style distributed rate limiter supporting multiple algorithms with Redis (Lua for atomicity) and Kafka for event-driven cache invalidation.

## ✨ Key Features

- 🔁 **Multiple Algorithms**
  - Fixed Window Counter
  - Sliding Window Log
  - Token Bucket
  - Leaky Bucket

- ⚡ **Atomic Operations with Redis + Lua**
  - No race conditions
  - High performance under concurrency

- 📦 **Rule-based Dynamic Configuration**
  - Rules stored in MySQL
  - Loaded via cache-aside pattern

- 🔄 **Event-driven Cache Invalidation (Kafka)**
  - Ensures consistency across multiple instances

- ⏳ **TTL-based Cleanup**
  - Prevents memory leaks in Redis

<img width="1080" height="857" alt="image" src="https://github.com/user-attachments/assets/c7d721b6-1894-49af-bed1-452ad3d42288" />

## 🔁 Flow

### Request Flow

- Client → API → RateLimiter
- RateLimiter → Redis (Lua)
- Redis (cache miss) → RuleService
- RuleService → MySQL

### Rule Update Flow

- DB → Kafka → All Instances
- All Instances → Redis.invalidate()

## 🧠 Core Concepts
### 1. Cache Aside Pattern
- Request → Redis
- → MISS → DB → Cache → Return
### 2. Event-Driven Cache Invalidation
- Rule Update → Kafka Event → All instances invalidate Redis
### 3. Atomic Rate Limiting
- Implemented via Lua scripts
- Ensures consistency across concurrent requests

## 📊 Algorithms Explained

| Algorithm       | Behavior                     | Use Case           |
|----------------|-----------------------------|--------------------|
| Fixed Window   | Hard limit per time window  | Simple APIs        |
| Sliding Log    | Precise rolling window      | Strict throttling  |
| Token Bucket   | Allows bursts, smooth refill| APIs with spikes   |
| Leaky Bucket   | Smooth outgoing rate        | Traffic shaping    |

## 🧪 API Endpoints
### 🔹 Test Rate Limiting
- GET /api/test
- Header: clientId: user_token

##  🔹 Update Rule
### PUT /rules/update
{
  "clientId": "user_token",
  "api": "/test",
  "algorithm": "TOKEN",
  "capacity": 3,
  "refillRate": 1
}

## 🧪 Testing
### 🔥 Burst Test (Windows CMD)
for /l %i in (1,1,10) do curl -H "clientId:user_token" http://localhost:8080/api/test
- Expected Output
- Success
- Success
- Success
- Too many requests

## ⚙️ Tech Stack
- Java 17 / Spring Boot
- Redis (Lua scripting)
- Kafka
- MySQL
- Docker
## 🐳 Running Locally
### 1. Start Redis
docker run -d -p 6379:6379 --name redis redis:7
### 2. Start Kafka (if using Docker)
docker run -d -p 9092:9092 apache/kafka

### 3. Run Application
mvn spring-boot:run

## 📌 Design Decisions
- Used Lua scripts instead of Java logic for atomic updates
- Used Kafka for distributed cache invalidation
- Used Redis TTL to auto-clean inactive users
- Separated rule config and runtime state
  
## 🧠 Interview Highlights
- Demonstrates distributed systems thinking
- Covers rate limiting algorithms deeply
- Uses event-driven architecture
- Shows real-world scalability patterns

## 👨‍💻 Author

## Vishal Shaw

## ⭐ If you found this useful, give it a star!
