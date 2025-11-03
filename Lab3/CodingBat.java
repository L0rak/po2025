package Lab3;

import java.util.Arrays;

public class CodingBat {

    public static boolean startHi(String str) {

        if (str.length() < 2) return false;

        String x = str.substring(0, 2);
        if (x.equals("hi")) {
            return true;
        }
        else  return false;
    }

    public static boolean makes10(int a, int b) {
        if (a == 10 || b == 10) return true;
        else if (a + b == 10) return true;
        else return false;
    }

    public static String[] fizzArray2(int n) {
        String[] elementy = new String[n];
        for (int i = 0; i<n; i++) {
            elementy[i] = Integer.toString(i);
        }
        return elementy;
    }

    public static String startWord(String str, String word) {

        if (str.length() >= 1) {
            int word_l = word.length();
            String word_t = word.substring(1);
            if (str.contains(word_t)) {
                return str.substring(0, word_l);
            }
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(startHi("hi there"));
        System.out.println(makes10(2, 8));
        System.out.println(Arrays.toString(fizzArray2(7)));
        System.out.println(startWord("kitten", "cit"));
    }


}
