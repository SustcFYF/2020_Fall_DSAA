//package homework1;

import java.util.Stack;

public class List {
    public ListNode headListNode;
    private int size;
    private int sorted;

    public List() {
        headListNode = null;
        size = 0;
        sorted = 0;
    }

    public List(ListNode node) {
        headListNode = node;
        size = 1;
        sorted = 0;
    }

    public int size() {
        return size;
    }

    public int sorted() {
        ListNode p = headListNode;
        boolean isAscending = true;
        boolean isDescending = true;
        for (int i = 0; i < size - 1; i++) {
            if (p.val > p.next.val) {
                isAscending = false;
            }
            if (p.val < p.next.val) {
                isDescending = false;
            }
            p = p.next;
        }
        if (isAscending) {
            sorted = 1;
        } else if (isDescending) {
            sorted = -1;
        } else sorted = 0;
        return sorted;//0-unsorted, 1-ascending, -1-descending
    }

    @Override
    public String toString() {
        String str = "[";
//str += val;
        ListNode pListNode = headListNode;
        while (pListNode.next != null) {
            str += pListNode.val + ", ";
            pListNode = pListNode.next;
        }
        str += pListNode.val + "]";
        return str;
    }

    public void sort() {
        ListNode p;
        int temp;
        for (int i = 0; i < size; i++) {
            p = headListNode;
            for (int j = 0; j < size - 1; j++) {
                if (p.val > p.next.val) {
                    temp = p.val;
                    p.val = p.next.val;
                    p.next.val = temp;
                }
                p = p.next;
            }
        }
        sorted = 1;
    }
    //sort the list ascending. Any sorting algorithm is OK.
    //attribute sorted should be changed to 1

    public void reverse() {
        Stack<ListNode> stack = new Stack<ListNode>();
        ListNode p = headListNode;
        while (p != null) {
            stack.push(p);
            p = p.next;
        }
        if (!stack.isEmpty())
            headListNode = stack.pop();
        p = headListNode;
        ListNode n;
        while (!stack.isEmpty()) {
            n = stack.pop();
            n.next = null;
            p.next = n;
            p = p.next;
        }
    }
    //reverse the order of nodes of list
    //attribute sorted should be changed if the list is sorted before

    public void addNode(ListNode node) {
        ListNode p = headListNode;
        while (p.next != null) {
            p = p.next;
        }
        p.next = node;
        size++;
    }
    //add node to tail of list

    public void addNodeSorted(ListNode node) {
        ListNode p = headListNode;
        if (sorted == 1) {
            if (node.val <= p.val) {
                addNode(0, node);
            } else {
                for (int i = 0; i < size - 1; i++) {
                    if ((p.val <= node.val) && (node.val <= p.next.val)) {
                        addNode(i + 1, node);
                        break;
                    }
                    p = p.next;
                }
                if ((p.next == null) && (node.val > p.val)) {
                    addNode(node);
                }
            }
        }
    }
    //add node to sorted list and keep list still sorted
    //node should add to the position according to the value

    public void addNode(int index, ListNode node) {
        ListNode p = headListNode;
        if (index == 0) {
            node.next = p;
            headListNode = node;
        } else {
            for (int i = 0; i < index - 1; i++) {
                p = p.next;
            }
            if (p.next != null) {
                node.next = p.next;
            }
            p.next = node;
        }
        size++;
    }
    //add node to position of index, which is from 0

    public boolean deleteNode(ListNode node) {
        ListNode p = headListNode;
        if (p.equals(node)) {
            headListNode = headListNode.next;
            size--;
            return true;
        }
        for (int i = 0; i < size - 1; i++) {
            if (p.next.equals(node)) {
                p.next = p.next.next;
                size--;
                return true;
            }
            p = p.next;
        }
        return false;
    }
    //delete node, return true if success, false if fail

    public boolean deleteNode(int index) {
        if (index < 0 || index > size - 1) {
            return false;
        } else {
            if (index == 0) {
                headListNode = headListNode.next;
            } else {
                ListNode p = headListNode;
                for (int i = 0; i < index - 1; i++) {
                    p = p.next;
                }
                if (index == size) {
                    p.next = null;
                } else {
                    p.next = p.next.next;
                }
            }
            size--;
            return true;
        }
    }
    //delete node at position index(from 0), return true if success, false if fail

    public void deleteDuplicates() {
        ListNode p = headListNode;
        while (p != null) {
            ListNode n = p;
            while (n.next != null) {
                if (p.val == n.next.val) {
                    n.next = n.next.next;
                    size--;
                } else {
                    n = n.next;
                }
            }
            p = p.next;
        }
    }
    //delete duplicated node from unsorted list

    public void deleteSortedDuplicates() {
        deleteDuplicates();
    }
    // delete duplicated node from sorted list

    public void mergeList(List listToMerge) {
        ListNode p = headListNode;
        while (p.next != null) {
            p = p.next;
        }
        p.next = listToMerge.headListNode;
        size += listToMerge.size;
    }
    //merge head of listToMerge to tail of original list

    public void mergeSortedList(List listToMerge) {
        mergeList(listToMerge);
        sort();
    }
    //merge to sorted lists and keep new list still sorted
}
