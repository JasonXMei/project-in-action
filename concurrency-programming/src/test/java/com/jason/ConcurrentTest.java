package com.jason;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author Jason
 * @Date 2022/12/05
 */
public class ConcurrentTest {


    @Test
    public void completeFutureTest() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        long start = System.currentTimeMillis();
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("completableFuture1 finished");
        }, executor);

        CompletableFuture<Void> completableFuture2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("completableFuture2 finished");
        }, executor);

        CompletableFuture<Void> completableFuture3 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("completableFuture3 finished");
        }, executor);

        try {
            CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3).get();
            long end = System.currentTimeMillis();
            System.out.println("completableFuture finished, spend: " + (end - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createThreadFactory() throws InterruptedException {
        ThreadFactory threadFactory = r -> {
            Thread thread = new Thread(r);
            thread.setName("thread-test");
            // 记录线程异常
            thread.setUncaughtExceptionHandler((t, e) -> {
                System.out.println(e.getMessage());
            });
            return thread;
        };

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100000),
                threadFactory);
        threadPool.execute(() -> {
            int i = 1 / 0;
        });

        threadPool.shutdown();
        System.out.println("pool shutdown: " + threadPool.isShutdown());
        while (!threadPool.isTerminated()) {
            threadPool.awaitTermination(1, TimeUnit.SECONDS);
        }
        System.out.println("all task complete");
    }

}
