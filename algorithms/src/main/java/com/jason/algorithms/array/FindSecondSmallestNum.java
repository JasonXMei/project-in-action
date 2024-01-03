package com.jason.algorithms.array;

import com.jason.algorithms.ArrayUtil;

/**
 * 找到数组中第二小的元素
 */
public class FindSecondSmallestNum {

    public static void main(String[] args) {
        int[] arr = ArrayUtil.generateArray(10, 100);
        ArrayUtil.printArray(arr);
        System.out.println(method1(arr));
    }

    public static int method1(int[] arr){
        if(arr.length < 2){
            throw new IllegalArgumentException("数组长度不能为空!");
        }
        int smallest = 0, secondSmallest = 0;
        if(arr[0] > 0){
            secondSmallest = arr[0];
        }else{
            smallest = arr[0];
        }

        for(int i=1;i<arr.length;i++){
            if(arr[i] < secondSmallest){
                if(arr[i] < smallest){
                    secondSmallest = smallest;
                    smallest = arr[i];
                }else{
                    secondSmallest = arr[i];
                }
            }
        }

        return secondSmallest;
    }
}
