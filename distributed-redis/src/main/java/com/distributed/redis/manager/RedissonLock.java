package com.distributed.redis.manager;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @desc 分布式锁实现基于Redisson
 */
@Slf4j
public class RedissonLock {

    RedissonManager redissonManager;

    public RedissonLock(RedissonManager redissonManager) {
        this.redissonManager = redissonManager;
    }

    public RedissonLock() {}
    /**
     * 加锁操作
     * @return
     */
    public boolean lock(String lockName, long expireSeconds) {
        RLock rLock = redissonManager.getRedisson().getLock(lockName);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock(0, expireSeconds, TimeUnit.SECONDS);
            if (getLock) {
                log.info("Obtain Redisson distributed lock success, lockName: [{}]", lockName);
            } else {
                log.info("Obtain Redisson distributed lock fail, lockName: [{}]", lockName);
            }
        } catch (InterruptedException e) {
            log.error("Obtain Redisson distributed lock fail, lockName: [{}]" + lockName, e);
            return false;
        }
        return getLock;
    }

    /**
     * 解锁
     * @param lockName
     */
    public void release(String lockName) {
        redissonManager.getRedisson().getLock(lockName).unlock();
    }

    public void setRedissonManager(RedissonManager redissonManager) {
        this.redissonManager = redissonManager;
    }

}
