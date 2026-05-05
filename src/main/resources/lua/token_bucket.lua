local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local rate = tonumber(ARGV[2])      -- tokens per ms
local now = tonumber(ARGV[3])

local data = redis.call("HMGET", key, "tokens", "last")
local tokens = tonumber(data[1])
local last = tonumber(data[2])

if tokens == nil then
  tokens = capacity
  last = now
end

local delta = math.max(0, (now - last) / 1000)
tokens = math.min(capacity, tokens + delta * rate)

local allowed = 0
if tokens >= 1 then
  tokens = tokens - 1
  allowed = 1
end

redis.call("HMSET", key, "tokens", tokens, "last", now)
redis.call("EXPIRE", key, 60) 
return allowed