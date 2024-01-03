package com.jason;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 抛出异常：add、remove、element
 * 返回结果但不抛出异常：offer、poll、peek
 * 阻塞：put、take
 * @Author Jason
 * @Date 2023-05-12
 */
public class BlockQueueDemo {

    static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(1);

    public static void main(String[] args) {
        add();
        remove();
        element();

        blockingQueue.offer(1);
        System.out.println(blockingQueue.peek());
        System.out.println(blockingQueue.poll());

        try {
            blockingQueue.put(1);
            System.out.println(blockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void add() {
        try {
            blockingQueue.add(1);
            blockingQueue.add(1);
        } catch (Exception e) {
            System.out.printf("add filed, e: %s \n", e.getMessage());
        }
    }

    public static void remove() {
        try {
            blockingQueue.remove();
            blockingQueue.remove();
        } catch (Exception e) {
            System.out.printf("remove filed, e: %s \n", e.getMessage());
        }
    }

    public static void element() {
        try {
            blockingQueue.element();
        } catch (Exception e) {
            System.out.printf("element filed, e: %s \n", e.getMessage());
        }
    }
}
