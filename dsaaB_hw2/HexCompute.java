//package Homework2;

public class HexCompute {
    public static String addStrings(String num1, String num2) {
        /*
        determine if the first digit of each string has a +/- symbol or 0
        delete the +/- symbols
         */
        if (num1.charAt(0) == '+')
            return addStrings(num1.substring(1), num2);
        if (num2.charAt(0) == '+')
            return addStrings(num1, num2.substring(1));
        if (num1.charAt(0) == '-' && num2.charAt(0) == '-')
            return '-' + addStrings(num1.substring(1), num2.substring(1));
        else if (num2.charAt(0) == '-')
            return minusStrings(num1, num2.substring(1));
        else if (num1.charAt(0) == '-')
            return minusStrings(num2, num1.substring(1));
        if (num1.charAt(0) == '0')
            return num2;
        if (num2.charAt(0) == '0')
            return num1;
        /*
         ensure num1 is longer
         */
        String t;
        if (num1.length() < num2.length()) {
            t = num1;
            num1 = num2;
            num2 = t;
        }
        /*
        do addition operation
         */
        char[] char1 = num1.toCharArray();
        char[] char2 = num2.toCharArray();
        int[] sum = new int[num1.length() + 1];
        boolean flag = true;
        int n1, n2;
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        for (int k = 0; k < num1.length(); k++) {
            n1 = Integer.parseInt(char1[i] + "", 16); // get integer from Hex
            if (flag) {
                n2 = Integer.parseInt(char2[j] + "", 16);
            } else n2 = 0;
            if (sum[k] + n1 + n2 < 16) {
                sum[k] += n1 + n2;
            } else {
                sum[k] += n1 + n2 - 16;
                sum[k + 1] += 1;
            }
            i--;
            if (j != 0) {
                j--;
            } else flag = false; // j is smaller than i, prevent out-of-boundary
        }
        /*
         get Hex string from sum array in the reverse order
         delete useless 0 at the beginning
         */
        String result = "";
        boolean flag1 = false;
        for (int k = sum.length - 1; k >= 0; k--) {
            if (sum[k] != 0 || flag1) {
                flag1 = true;
                result += Integer.toHexString(sum[k]).toUpperCase();
            }
        }
        return result;
    }

    public static String minusStrings(String num1, String num2) {
        /*
         compare num1 and num2 to ensure num1 is larger
         */
        boolean isSwap = false; // record whether it swap or not
        String t;
        if (num1.length() < num2.length() || (num1.length() == num2.length() && num1.charAt(0) < num2.charAt(0))) {
            t = num1;
            num1 = num2;
            num2 = t;
            isSwap = true;
        }
        /*
         do minus operation
         */
        char[] char1 = num1.toCharArray();
        char[] char2 = num2.toCharArray();
        int[] minus = new int[num1.length()];
        boolean flag = true;
        int n1, n2;
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        for (int k = 0; k < num1.length(); k++) {
            n1 = Integer.parseInt(char1[i] + "", 16); // get integer from Hex
            if (flag) {
                n2 = Integer.parseInt(char2[j] + "", 16);
            } else n2 = 0;
            if (minus[k] + n1 - n2 >= 0) {
                minus[k] += n1 - n2;
            } else {
                minus[k] += n1 - n2 + 16;
                minus[k + 1] -= 1;
            }
            if (i != 0) i--;
            if (j != 0) {
                j--;
            } else flag = false; // j is smaller than i, prevent out-of-boundary
        }
        /*
         get Hex string from minus array in the reverse order
         delete useless 0 at the beginning
         */
        String result = "";
        boolean flag1 = false;
        for (int k = minus.length - 1; k >= 0; k--) {
            if (minus[k] != 0 || flag1) {
                flag1 = true;
                result += Integer.toHexString(minus[k]).toUpperCase();
            }
        }
        if (isSwap) result = '-' + result;
        return result;
    }

    public static String multiply(String num1, String num2) {
        /*
         determine if the first digit of each string has a +/- symbol or 0
         delete the +/- symbols
         */
        if (num1.charAt(0) == '0' || num2.charAt(0) == '0')
            return "0";
        if (num1.charAt(0) == '+')
            return multiply(num1.substring(1), num2);
        if (num2.charAt(0) == '+')
            return multiply(num1, num2.substring(1));
        if (num1.charAt(0) == '-' && num2.charAt(0) == '-')
            return multiply(num1.substring(1), num2.substring(1));
        else if (num1.charAt(0) == '-')
            return '-' + multiply(num1.substring(1), num2);
        else if (num2.charAt(0) == '-')
            return '-' + multiply(num1, num2.substring(1));
        /*
         ensure num1 is longer
         */
        String t;
        if (num1.length() < num2.length()) {
            t = num1;
            num1 = num2;
            num2 = t;
        }
        char[] char1 = num1.toCharArray();
        char[] char2 = num2.toCharArray();
        int[] product = new int[num1.length() + num2.length()];
        int n1, n2, k;
        for (int j = num2.length() - 1; j >= 0; j--) {
            for (int i = num1.length() - 1; i >= 0; i--) {
                n1 = Integer.parseInt(char1[i] + "", 16); // get integer from Hex
                n2 = Integer.parseInt(char2[j] + "", 16);
                k = num1.length() + num2.length() - i - j - 2; // the index of product array
                product[k + 1] += (product[k] + n1 * n2) / 16;
                product[k] = (product[k] + n1 * n2) % 16;
            }
        }
        /*
         get Hex string from sum array in the reverse order
         delete useless 0 at the beginning
         */
        String result = "";
        boolean flag = false;
        for (int i = product.length - 1; i >= 0; i--) {
            if (product[i] != 0 || flag) {
                flag = true;
                result += Integer.toHexString(product[i]).toUpperCase();
            }
        }
        return result;
    }
}
