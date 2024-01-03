package com.jason;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Jason
 * @Date 2023-05-11
 */
public class CustomTheadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String threadNameFomat;

    public CustomTheadFactory(String threadNameFomat) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        this.threadNameFomat = threadNameFomat;
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, String.format(threadNameFomat, threadNumber.getAndIncrement()),0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }

}
