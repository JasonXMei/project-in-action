package com.distributed.redis.controller;

import com.distributed.redis.manager.RedissonLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RedissonLockController {

    @Autowired
    RedissonLock redissonLock;

    @GetMapping("lock")
    public String lock(@RequestParam("lockName") String lockName) throws InterruptedException {
        if (redissonLock.lock(lockName, 10)) {
            Thread.sleep(5000);
            redissonLock.release(lockName);
            return "ok";
        } else {
            return "fail";
        }
    }

}
