package com.jason;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 建议强制传线程池，且根据实际情况做线程池隔离,减少不同业务之间的相互干扰 *
 */
public class CompletableFutureTest {

    ExecutorService executor = Executors.newFixedThreadPool(16);

    @Test
    public void zeroDependent() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "result1", executor);
        System.out.println(cf1.get());
    }

    @Test
    public void oneDependent() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "result1", executor);

        // 单个CompletableFuture的依赖可以通过thenApply、thenAccept、thenCompose等方法来实现
        CompletableFuture<String> cf2 = cf1.thenApply(result1 -> result1 + "," + "result2");

        System.out.println(cf2.get());
    }

    @Test
    public void twoDependents() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "result1", executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "result2", executor);

        // 二元依赖可以通过thenCombine等回调来实现
        CompletableFuture<String> cf3 = cf1.thenCombine(cf2, (result1, result2) -> result1 + "," + result2);
        System.out.println(cf3.get());
    }

    @Test
    public void manyDependents() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "result1", executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "result2", executor);
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> "result3", executor);

        // 多元依赖可以通过allOf或anyOf方法来实现，区别是当需要多个依赖全部完成时使用allOf，当多个依赖中的任意一个完成即可时使用anyOf
        CompletableFuture<Void> cf4 = CompletableFuture.allOf(cf1, cf2, cf3);
        CompletableFuture<String> result = cf4.thenApply(v -> {
            //这里的join并不会阻塞，因为传给thenApply的函数是在CF1、CF2、CF3全部完成时，才会执行 。
            String result1 = cf1.join();
            String result2 = cf2.join();
            String result3 = cf3.join();
            return result1 + "," + result2 + "," + result3;
        });
        System.out.println(result.get());
    }

    @Test
    public void executeThread() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync 执行线程：" + Thread.currentThread().getName());
            //业务操作
            return "";
        }, executor);

        //此时，如果future1中的业务操作已经执行完毕并返回，则该thenApply直接由当前main线程执行；否则，将会由执行以上业务操作的threadPool1中的线程执行。
        // System.out.println(future1.get());
        future1.thenApply(value -> {
            System.out.println("thenApply 执行线程：" + Thread.currentThread().getName());
            return value + "1";
        });

        //使用ForkJoinPool中的共用线程池CommonPool，CommonPool的大小是CPU核数-1，如果是IO密集的应用，线程数可能成为瓶颈
        future1.thenApplyAsync(value -> {
            System.out.println("thenApplyAsync 执行线程(不指定线程池)：" + Thread.currentThread().getName());
            //do something
            return value + "1";
        });

        //使用指定线程池
        future1.thenApplyAsync(value -> {
            System.out.println("thenApplyAsync 执行线程（指定线程池）：" + Thread.currentThread().getName());
            //do something
            return value + "1";
        }, executor);
    }

    @Test
    public void tes() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            //业务操作
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "";
        }, executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            //业务操作
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "";
        }, executor);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            //业务操作
            try {
                Thread.sleep(6000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "";
        }, executor);


        future1.get();
        future2.get();
        future3.get();
    }


}
