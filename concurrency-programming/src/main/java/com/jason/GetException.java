package com.jason;

import java.util.concurrent.*;

/**
 * 1) 即便任务抛出异常，isDone 方法依然会返回 true；
 * 2) 虽然抛出的异常是 IllegalArgumentException，但是对于 get 而言，它抛出的异常依然是 ExecutionException；
 * 3) 虽然在任务执行一开始时就抛出了异常，但是真正要等到我们执行 get 的时候，才看到了异常
 * @Author Jason
 * @Date 2023-05-11
 */
public class GetException {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(20);
        Future<Integer> future = service.submit(new CallableTask());

        try {
            Thread.sleep(500);
            System.out.println(future.isDone());
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }


    static class CallableTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            throw new IllegalArgumentException("Callable抛出异常");
        }
    }

}
