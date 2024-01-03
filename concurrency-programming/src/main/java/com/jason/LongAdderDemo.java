package com.jason;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder 引入了分段锁的概念，当竞争不激烈的时候，所有线程都是通过 CAS 对同一个 Base 变量进行修改，但是当竞争激烈的时候，LongAdder 会把不同线程对应到不同的 Cell 上进行修改，降低了冲突的概率，从而提高了并发性
 * 如果我们的场景仅仅是需要用到加和减操作的话，那么可以直接使用更高效的 LongAdder，但如果我们需要利用 CAS 比如 compareAndSet 等操作的话，就需要使用 AtomicLong 来完成
 * @Author Jason
 * @Date 2023-05-12
 */
public class LongAdderDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //longAdderRun();
        atomicLongRun();
        long end = System.currentTimeMillis();
        System.out.println("spend: " + (end - start));
    }

    public static void atomicLongRun() {
        ExecutorService service = Executors.newFixedThreadPool(32);
        AtomicLong counter = new AtomicLong();
        for (int i = 0; i < 100000; i++) {
            service.submit(new Task1(counter));
        }

        service.shutdown();
        while (!service.isTerminated()) {
        }
        System.out.println(counter.get());
    }

    public static void longAdderRun() {
        ExecutorService service = Executors.newFixedThreadPool(32);
        LongAdder counter = new LongAdder();
        for (int i = 0; i < 100000; i++) {
            service.submit(new Task(counter));
        }

        service.shutdown();
        while (!service.isTerminated()) {
        }
        System.out.println(counter.sum());
    }

    static class Task implements Runnable {

        private final LongAdder counter;

        public Task(LongAdder counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            counter.increment();
        }

    }

    static class Task1 implements Runnable {

        private final AtomicLong counter;

        public Task1(AtomicLong counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            counter.incrementAndGet();
        }

    }

}
