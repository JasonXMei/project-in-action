package com.jason.algorithms.string;

/**
 * 反转 String
 * @Author Jason
 * @Date 2023-05-16
 */
public class ReverseString {

    public static String reverse(String s) {
        if (s.length() == 1) {
            return s;
        }

        char[] arr = s.toCharArray();
        int i = 0,j = arr.length - 1;

        char[] reverseArr = new char[arr.length];
        while (i <= j) {
            reverseArr[i] = arr[j];
            reverseArr[j] = arr[i];
            i++;
            j--;
        }

        return new String(reverseArr);
    }

    public static void main(String[] args) {
        System.out.println(reverse("j"));
        System.out.println(reverse("ja"));
        System.out.println(reverse("jason"));
    }
}
