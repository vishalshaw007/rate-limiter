local key = KEYS[1]
local limit = tonumber(ARGV[1])
local window = tonumber(ARGV[2])
local now = tonumber(ARGV[3])

local start = now - window
redis.call("ZREMRANGEBYSCORE", key, 0, start)
local count = redis.call("ZCARD", key)

if count < limit then
  redis.call("ZADD", key, now, now)
  redis.call("PEXPIRE", key, window)
  return 1
else
  return 0
end