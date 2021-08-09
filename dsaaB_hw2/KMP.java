//package Homework2;

import java.util.LinkedList;

public class KMP {
    /*
     calculate the next array of P
     */
    public static int[] computeNextArray(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = -1;
        int i = 0;
        int j = -1;
        while (i < pattern.length() - 1) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) { // match successfully
                next[++i] = ++j;
            } else {
                j = next[j]; // not match, move pointer j
            }
        }
        return next;
    }

    /*
    calculate the times P appears in S
     */
    public static int KMPSearchTimes(String text, String pattern) {
        /*
        use the next array implemented in the first question
        */
        LinkedList<Integer> location = KMPFindLocations(text, pattern);
        return location.size(); // the number of times is the size of list
    }

    /*
     calculate the position P appears in S
     */
    public static LinkedList<Integer> KMPFindLocations(String text, String pattern) {
        /*
        use the next array implemented in the first question
        */
        LinkedList<Integer> location = new LinkedList<>();
        int[] next = computeNextArray(pattern);
        int i = 0;
        int j = 0;
        while (i < text.length() && j < pattern.length()) {
            if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pattern.length()) { // find one P in S
                    location.add(i - pattern.length());
                    i = i - pattern.length() + 1; // avoid cases like P = aba, S = ababa
                    j = 0;
                }
            } else {
                j = next[j]; // move to next pointer
            }
        }
        return location;
    }
}