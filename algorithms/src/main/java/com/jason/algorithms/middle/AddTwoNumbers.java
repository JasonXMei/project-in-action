package com.jason.algorithms.middle;

import java.util.Stack;

/**
 * 给出两个非空的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字0之外，这两个数都不会以0开头。
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class AddTwoNumbers {

    public static void main(String[] args) {
        ListNode listNode1 = initListNode(2,4,3,2,4,7,1,4,1,9,7,8,3,5,1,7,8,2,7,2,6,8,9,1,5,7,8);
        printListNode(listNode1);
        ListNode listNode2 = initListNode(5,6,4,2,4,7,1,4,1,9,7,8,3,5,1,7,8,2,7,2,6);
        printListNode(listNode2);
        ListNode sum = method1(listNode1, listNode2);
        printListNode(sum);

        sum = method2(listNode1, listNode2);
        printListNode(sum);
    }

    /**
     * ListNode元素以逆序方式存储
     */
    public static ListNode method1(ListNode listNode1, ListNode listNode2){
        if(listNode1 == null) return listNode2;
        if(listNode2 == null) return listNode1;
        ListNode dummyListNode = new ListNode(0);
        ListNode currentListNode = dummyListNode;
        int carryFlag = 0;
        do{
            int x = listNode1 == null ? 0 : listNode1.val;
            int y = listNode2 == null ? 0 : listNode2.val;
            int sum = x + y + carryFlag;
            currentListNode.next = new ListNode(sum % 10);
            currentListNode = currentListNode.next;
            carryFlag = sum / 10;
            listNode1 = listNode1 == null ? null : listNode1.next;
            listNode2 = listNode2 == null ? null : listNode2.next;
        }while(listNode1 != null || listNode2 != null);

        if(carryFlag == 1){
            currentListNode.next = new ListNode(carryFlag);
        }
        return  dummyListNode.next;
    }

    /**
     * ListNode元素以顺序方式存储
     */
    public static ListNode method2(ListNode listNode1, ListNode listNode2){
        if(listNode1 == null) return listNode2;
        if(listNode2 == null) return listNode1;

        Stack<Integer> stack = new Stack<>();

        Stack<Integer> stack1 = new Stack<>();
        pushStack(listNode1, stack1);
        Stack<Integer> stack2 = new Stack<>();
        pushStack(listNode2, stack2);

        int carryFlag = 0;
        while(!stack1.empty() || !stack2.empty()){
            int x = stack1.empty() ? 0 : stack1.pop();
            int y = stack2.empty() ? 0 : stack2.pop();
            int sum = x + y + carryFlag;

            stack.push(sum % 10);
            carryFlag = sum / 10;
        }

        if(carryFlag == 1){
            stack.push(carryFlag);
        }

        ListNode listNode = stack2ListNode(stack);
        return listNode;
    }

    public static void pushStack(ListNode listNode, Stack<Integer> stack){
        stack.push(listNode.val);
        if(listNode.next != null) pushStack(listNode.next, stack);
    }

    public static ListNode stack2ListNode(Stack<Integer> stack){
        ListNode dummyListNode = new ListNode(0);
        ListNode currentListNode = dummyListNode;
        while(!stack.empty()){
            currentListNode.next = new ListNode(stack.pop());
            currentListNode = currentListNode.next;
        }
        return dummyListNode.next;
    }

    public static void printListNode(ListNode listNode){
        System.out.print(listNode.val + "\t");
        if(listNode.next != null){
            printListNode(listNode.next);
        }else{
            System.out.println();
        }
    }

    public static ListNode initListNode(int... params){
        ListNode listNode = new ListNode(0);
        ListNode currentListNode = listNode;
        for(int i : params){
            currentListNode.next = new ListNode(i);
            currentListNode = currentListNode.next;
        }
        return listNode.next;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }
}
