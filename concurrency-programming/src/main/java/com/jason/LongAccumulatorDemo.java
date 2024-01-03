package com.jason;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * 适用场景
 * 第一点需要满足的条件，就是需要大量的计算，并且当需要并行计算的时候
 * 第二点需要满足的要求，就是计算的执行顺序并不关键, 比如加法，乘法，求最大值，最小值
 * @Author Jason
 * @Date 2023-05-12
 */
public class LongAccumulatorDemo {

    public static void main(String[] args) throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 0);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        IntStream.range(1, 10).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

        Thread.sleep(2000);
        System.out.println(accumulator.getThenReset());
    }

}
