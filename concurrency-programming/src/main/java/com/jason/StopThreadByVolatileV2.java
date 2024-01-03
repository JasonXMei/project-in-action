package com.jason;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * volatile 修饰标记位不适用的场景
 * @Author Jason
 * @Date 2023-04-28
 */
public class StopThreadByVolatileV2 {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue storage = new ArrayBlockingQueue(8);

        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(500);

        Consumer consumer = new Consumer(storage);
        while (consumer.needMoreNums()) {
            System.out.println(consumer.storage.take() + "被消费了");
            Thread.sleep(100);
            consumer.wait();
        }
        System.out.println("消费者不需要更多数据了。");

        // 一旦消费不需要更多数据了，我们应该让生产者也停下来，但是实际情况却停不下来
        // 生产者在执行 storage.put(num) 时发生阻塞，在它被叫醒之前是没有办法进入下一次循环判断 canceled 的值的，所以在这种情况下用 volatile 是没有办法让生产者停下来的，
        // 相反如果用 interrupt 语句来中断，即使生产者处于阻塞状态，仍然能够感受到中断信号，并做响应处理
        producer.canceled = true;
        System.out.println(producer.canceled);
    }

    static class Producer implements Runnable {
        public volatile boolean canceled = false;
        BlockingQueue storage;
        public Producer(BlockingQueue storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            int num = 0;
            try {
                while (num <= 100000 && !canceled) {
                    if (num % 50 == 0) {
                        storage.put(num);
                        System.out.println(num + "是50的倍数,被放到仓库中了。");
                    }
                    num++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("生产者结束运行");
            }
        }

    }

    static class Consumer {
        BlockingQueue storage;
        public Consumer(BlockingQueue storage) {
            this.storage = storage;
        }
        public boolean needMoreNums() {
            if (Math.random() > 0.97) {
                return false;
            }
            return true;
        }
    }
}
