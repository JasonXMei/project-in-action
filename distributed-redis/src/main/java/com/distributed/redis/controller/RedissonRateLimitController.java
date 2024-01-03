package com.distributed.redis.controller;

import com.distributed.redis.manager.RedissonRateLimit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RedissonRateLimitController {

    @Autowired
    RedissonRateLimit redissonRateLimit;

    @GetMapping("rateLimit")
    public String rateLimit(@RequestParam("limitName") String limitName) {
        // 每 1 秒内执行 3 个请求
        RRateLimiter rateLimit = redissonRateLimit.getRateLimiter(limitName, RateType.OVERALL, 3L, 10L, RateIntervalUnit.SECONDS);
        if (rateLimit.tryAcquire()) {
            return "ok";
        } else {
            return "rate limit";
        }
    }

}
