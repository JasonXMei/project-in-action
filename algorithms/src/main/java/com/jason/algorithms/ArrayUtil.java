package com.jason.algorithms;

import java.util.Random;

/**
 * Created by abo on 2019/8/15.
 */
public class ArrayUtil {

    public static int[] generateArray(int length, int maxNumber){
        int[] arr = new int[length];
        Random random = new Random();
        for(int i=0;i<length;i++){
            arr[i] = random.nextInt(maxNumber);
        }
        return arr;
    }

    public static void printArray(int[] arr){
        for(int i : arr){
            System.out.print(i + "\t");
        }
        System.out.println();
    }
}
