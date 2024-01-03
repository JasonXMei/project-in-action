package com.distributed.redis.manager;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

/**
 * @desc 分布式限流实现基于Redisson
 */
@Slf4j
public class RedissonRateLimit {

    RedissonManager redissonManager;

    public RedissonRateLimit(RedissonManager redissonManager) {
        this.redissonManager = redissonManager;
    }

    public RedissonRateLimit() {}

    /**
     * Initializes RateLimiter's state and stores config to Redis server.
     *
     * @param name - rate limiter name
     * @param mode - rate mode
     * @param rate - rate
     * @param rateInterval - rate time interval
     * @param rateIntervalUnit - rate time interval unit
     * @return {@code true} if rate was set and {@code false} otherwise
     */
    public RRateLimiter getRateLimiter(String name, RateType mode, Long rate, Long rateInterval, RateIntervalUnit rateIntervalUnit) {
        RRateLimiter rateLimiter = redissonManager.getRedisson().getRateLimiter(name);
        boolean setRate = rateLimiter.trySetRate(mode, rate, rateInterval, rateIntervalUnit);
        if (setRate) {
            log.info("Redisson Rate Set Success, rateLimiterName: [{}]", name);
        }
        return rateLimiter;
    }

    public void setRedissonManager(RedissonManager redissonManager) {
        this.redissonManager = redissonManager;
    }

}
