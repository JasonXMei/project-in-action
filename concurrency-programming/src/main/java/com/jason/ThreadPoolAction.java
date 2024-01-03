package com.jason;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Jason
 * @Date 2023-05-11
 */
public class ThreadPoolAction {

    public static void main(String[] args) throws InterruptedException {
        // corePoolSize -> workQueue size -> maximumPoolSize -> rejectedExecutionHandler
        // 线程数 = CPU 核心数 *（1 + 任务平均等待时间/任务平均工作时间）
        ExecutorService service = new ThreadPoolExecutor(5, 10, 1000L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new CustomTheadFactory("order-pool-%d"),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i=0;i<5;i++) {
            service.submit(new Task());
        }

        service.shutdown();

        while (!service.isTerminated()) {
            System.out.println("thread pool task not finished");
            Thread.sleep(1000L);
        }

        System.out.println("thread pool task finished");


    }

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }


}
