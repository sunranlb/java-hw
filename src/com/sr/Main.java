package com.sr;

import java.util.*;

public class Main {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while () {
        }
        return null;
    }

    public static void main(String[] args) {
        Main m = new Main();
        System.out.println(m.coinChange(new int[]{1, 2, 5}, 11));
        System.out.println(m.coinChange(new int[]{2}, 3));
        System.out.println(m.coinChange(new int[]{2}, 0));
        System.out.println(m.coinChange(new int[]{186,419,83,408}, 6249));
    }
}
