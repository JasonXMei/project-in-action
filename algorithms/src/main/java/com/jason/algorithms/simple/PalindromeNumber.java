package com.jason.algorithms.simple;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数
 */
public class PalindromeNumber {

    public static void main(String[] args) {
        int i = 1221;
        System.out.println(method1(i));
        System.out.println(method2(i));
    }

    public static boolean method1(int number){
        String str = String.valueOf(number);
        int length = str.length();
        boolean flag = true;
        for(int i=0;i<length/2;i++){
            char start = str.charAt(i);
            char end = str.charAt(length - 1 - i);
            if(start != end){
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 直观上来看待回文数的话，就感觉像是将数字进行对折后看能否一一对应。
     * 所以这个解法的操作就是 取出后半段数字进行翻转。
     * 这里需要注意的一个点就是由于回文数的位数可奇可偶，所以当它的长度是偶数时，它对折过来应该是相等的；
     * 当它的长度是奇数时，那么它对折过来后，有一个的长度需要去掉一位数（除以 10 并取整）。
     * 具体做法如下：
     * 每次进行取余操作 （ %10），取出最低的数字：y = x % 10
     * 将最低的数字加到取出数的末尾：revertNum = revertNum * 10 + y
     * 每取一个最低位数字，x 都要自除以 10
     * 判断 x 是不是小于 revertNum ，当它小于的时候，说明数字已经对半或者过半了
     * 最后，判断奇偶数情况：如果是偶数的话，revertNum 和 x 相等；如果是奇数的话，最中间的数字就在revertNum 的最低位上，
     * 将它除以 10 以后应该和 x 相等。
     */
    public static boolean method2(int number){
        if(number < 0 || (number % 10 == 0 && number != 0)){
            return false;
        }

        int revertNumber = 0;
        while(revertNumber < number){
            revertNumber = revertNumber * 10 + number%10;
            number = number/10;
        }

        return (revertNumber == number) || (revertNumber/10 == number);
    }
}
