local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local rate = tonumber(ARGV[2])   -- leak per ms
local now = tonumber(ARGV[3])

local data = redis.call("HMGET", key, "q", "last")
local q = tonumber(data[1])
local last = tonumber(data[2])

if q == nil then
  q = 0
  last = now
end

local delta = math.max(0, (now - last) / 1000)
q = math.max(0, q - delta * rate)

local allowed = 0
if q < capacity then
  q = q + 1
  allowed = 1
end

redis.call("HMSET", key, "q", q, "last", now)
redis.call("EXPIRE", key, 60) 
return allowed