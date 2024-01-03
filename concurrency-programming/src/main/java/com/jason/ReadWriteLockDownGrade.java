package com.jason;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 只能从写锁降级为读锁，不能从读锁升级为写锁
 *
 * 写锁降级读锁可以同时被多个线程获取，提升性能
 * 读锁可以同时被多个线程持有，升级写锁需要释放所有读锁，可能造成死锁问题。
 * @Author Jason
 * @Date 2023-05-11
 */
public class ReadWriteLockDownGrade {

    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> downGrade(),"Thread-1").start();
        Thread.sleep(1000L);

        new Thread(() -> getReadlock(),"Thread-2").start();
        new Thread(() -> getReadlock(),"Thread-3").start();
    }

    public static void downGrade() {
        rwl.writeLock().lock();
        System.out.printf("%s 获取到了写锁\n", Thread.currentThread().getName());

        //在不释放写锁的情况下，直接获取读锁，这就是读写锁的降级。
        rwl.readLock().lock();
        System.out.printf("%s 成功降级, 获取到了读锁\n", Thread.currentThread().getName());

        rwl.writeLock().unlock();
        System.out.printf("%s 释放了写锁\n", Thread.currentThread().getName());
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getReadlock() {
        rwl.readLock().lock();
        System.out.printf("%s 获取到了读锁\n", Thread.currentThread().getName());
    }
}
