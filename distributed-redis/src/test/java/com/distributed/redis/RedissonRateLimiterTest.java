package com.distributed.redis;

import com.distributed.redis.manager.RedissonRateLimit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonRateLimiterTest {


    @Autowired
    RedissonRateLimit redissonRateLimit;

    @Test
    public void testRateValue() throws InterruptedException {
        RRateLimiter limiter = redissonRateLimit.getRateLimiter("test1", RateType.OVERALL, 5L, 1L, RateIntervalUnit.SECONDS);

        assertThat(limiter.availablePermits()).isEqualTo(5);

        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();
        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();
        assertThat(limiter.availablePermits()).isEqualTo(3);
        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();
        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();
        assertThat(limiter.availablePermits()).isEqualTo(1);
        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();

        assertThat(limiter.availablePermits()).isEqualTo(0);

        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isFalse();

        Thread.sleep(1000);

        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();
        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();
        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();
        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();
        assertThat(limiter.tryAcquire(1, 0, TimeUnit.SECONDS)).isTrue();
    }

}
