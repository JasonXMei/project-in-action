package com.jason;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 使用场景
 * 第一种情况是出于历史原因考虑，那么如果出于历史原因的话，之前这个变量已经被声明过了而且被广泛运用，那么修改它成本很高，所以我们可以利用升级的原子类。
 * 还有一个使用场景，如果我们在大部分情况下并不需要使用到它的原子性，只在少数情况，比如每天只有定时一两次需要原子操作的话，我们其实没有必要把原来的变量声明为原子类型的变量，
 * 因为 AtomicInteger 比普通的变量更加耗费资源。
 *
 * @Author Jason
 * @Date 2023-05-12
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable {

    static Score math = new Score();
    static Score computer = new Score();

    public static AtomicIntegerFieldUpdater<Score> scoreUpdater = AtomicIntegerFieldUpdater
            .newUpdater(Score.class, "score");

    @Override
    public void run() {
        for (int i = 0; i < 5000; i++) {
            computer.score++;
            scoreUpdater.getAndIncrement(math);
        }
    }

    public static class Score {
        volatile int score;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerFieldUpdaterDemo r = new AtomicIntegerFieldUpdaterDemo();

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("普通变量的结果：" + computer.score);
        System.out.println("升级后的结果：" + math.score);
    }
}
