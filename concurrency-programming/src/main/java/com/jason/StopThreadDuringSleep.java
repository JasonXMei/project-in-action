package com.jason;

/**
 * 如果 sleep、wait 等可以让线程进入阻塞的方法使线程休眠了，而处于休眠中的线程被中断，那么线程是可以感受到中断信号的，并且会抛出一个 InterruptedException 异常，同时清除中断信号，将中断标记位设置成 false。
 * 这样一来就不用担心长时间休眠中线程感受不到中断了，因为即便线程还在休眠，仍然能够响应中断通知，并抛出异常。
 *
 * @Author Jason
 * @Date 2023-04-28
 */
public class StopThreadDuringSleep implements Runnable {

    @Override
    public void run() {
        int num = 0;
        while (!Thread.currentThread().isInterrupted() && num <= 1000) {
            try {
                System.out.println(num);
                num++;
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                // 线程在休眠期间被中断，那么会自动清除中断信号
                e.printStackTrace();

                // 手动添加中断信号
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadDuringSleep());
        thread.start();
        Thread.sleep(5);
        thread.interrupt();
    }

}
