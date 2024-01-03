package com.jason;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 互斥条件、请求与保持条件、不剥夺条件和循环等待条件
 *
 * 修复策略：
 * 一是避免策略，其主要思路就是去改变锁的获取顺序，防止相反顺序获取锁这种情况的发生；
 * 二是检测与恢复策略，它是允许死锁发生，但是一旦发生之后它有解决方案；
 * 三是鸵鸟策略，不管他，出现低概率死锁问题重启
 * @Author Jason
 * @Date 2023-05-14
 */
public class DeadLock {

    Object o1 = new Object();
    Object o2 = new Object();

    private void deadLock() {
        Thread t1 = new Thread(() -> {
            synchronized (o1) {
                try {
                    Thread.sleep(1000L);
                    synchronized (o2) {
                        System.out.println("t1 started!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (o2) {
                try {
                    Thread.sleep(1000L);
                    synchronized (o1) {
                        System.out.println("t2 started!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

    public static void main(String[] args) throws InterruptedException {
        new DeadLock().deadLock();

        Thread.sleep(2000);

        // ThreadMXBean 也可以帮我们找到并定位死锁，如果我们在业务代码中加入这样的检测，那我们就可以在发生死锁的时候及时地定位，
        // 同时进行报警等其他处理，也就增强了我们程序的健壮性
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            for (int i = 0; i < deadlockedThreads.length; i++) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThreads[i]);
                System.out.println("线程id为" + threadInfo.getThreadId() + ",线程名为" + threadInfo.getThreadName() + "的线程已经发生死锁，需要的锁正被线程" + threadInfo.getLockOwnerName() + "持有。");
            }
        }
    }
}
