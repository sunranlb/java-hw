package com.sr;

public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public static ListNode array2ListNode(int[] a) {
        ListNode res = null;
        ListNode pt = null;
        for (int i = 0; i < a.length; i++) {
            if (res == null) {
                res = new ListNode(a[i]);
                pt = res;
            } else {
                pt.next = new ListNode(a[i]);
                pt = pt.next;
            }
        }
        return res;
    }

}
