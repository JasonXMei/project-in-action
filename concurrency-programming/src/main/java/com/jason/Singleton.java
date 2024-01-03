package com.jason;

/**
 * @Author Jason
 * @Date 2023-05-14
 */
public class Singleton {

    // volatile 防止避免拿到没完成初始化的对象，保证线程安全
    private static volatile Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        // 第一个 check 而言，如果去掉它，那么所有线程都会串行执行，效率低下
        if (singleton == null) {
            synchronized (Singleton.class) {
                // 避免多线程情况下重复创建对象
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}
