package com.jason;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * wait/notify 实现
 * @Author Jason
 * @Date 2023-04-28
 */
public class ProducerAndConsumerV3 {

    private int maxSize;
    private LinkedList<Integer> storage;

    public ProducerAndConsumerV3(int size) {
        this.maxSize = size;
        storage = new LinkedList<>();
    }

    public synchronized void put(Integer i) throws InterruptedException {
        while (storage.size() == maxSize) {
            wait();
        }

        storage.add(i);
        notifyAll();
    }

    public synchronized Integer take() throws InterruptedException {
        while (storage.size() == 0) {
            wait();
        }
        Integer i = storage.remove();
        notifyAll();
        return i;
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerAndConsumerV3 pc = new ProducerAndConsumerV3(10);
        AtomicInteger atomicInteger = new AtomicInteger();

        Runnable producer = () -> {
            while (true) {
                try {
                    pc.put(atomicInteger.getAndIncrement());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(producer).start();
        new Thread(producer).start();

        Runnable consumer = () -> {
            while (true) {
                try {
                    System.out.println("consume: " + pc.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(consumer).start();
        new Thread(consumer).start();
    }
}
