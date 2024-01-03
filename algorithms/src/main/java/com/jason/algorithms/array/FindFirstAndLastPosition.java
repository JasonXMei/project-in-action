package com.jason.algorithms.array;

import com.jason.algorithms.ArrayUtil;

/**
 * leetcode 34
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 * 
 * 示例 1：
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 *  
 * 示例 2：
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 *  
 * 示例 3：
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 *  
 * 提示：
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 * nums 是一个非递减数组
 * -109 <= target <= 109
 */
public class FindFirstAndLastPosition {

    public static int[] searchRange(int[] nums, int target) {
        int l = search(nums, target);
        int r = search(nums, target + 1);
        return l == r ? new int[]{-1, -1} : new int[]{l, r - 1};
    }

    private static int search(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 7, 7, 9, 10};
        ArrayUtil.printArray(searchRange(nums, 7));
        ArrayUtil.printArray(searchRange(nums, 9));

        int[] nums1 = new int[]{5, 7, 7, 8, 8, 10};
        ArrayUtil.printArray(searchRange(nums1, 8));
        ArrayUtil.printArray(searchRange(nums1, 6));

        int[] nums2 = new int[]{};
        ArrayUtil.printArray(searchRange(nums2, 0));
    }

}
