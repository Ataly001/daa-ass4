import java.util.*;

public class Tests {
    static int total = 0;

    static void check(boolean ok, String name) {
        total++;
        if (!ok) throw new RuntimeException("Failed: " + name);
        System.out.println("Passed: " + name);
    }

    static void match(String s, String p, Integer... a) {
        ArrayList<Integer> x = new ArrayList<>(Arrays.asList(a));
        check(Naive.search(s, p).equals(x), "Naive " + total);
        check(Kmp.search(s, p).equals(x), "KMP " + total);
        check(Rk.search(s, p).equals(x), "RK " + total);
    }

    public static void main(String[] args) {
        check(Lcs.solve("abc", "abc") == 3, "LCS identical");
        check(Lcs.solve("abc", "xyz") == 0, "LCS different");
        check(Lcs.solve("", "abc") == 0, "LCS empty");
        check(Lcs.solve("abcde", "ace") == 3, "LCS normal");
        check(Lcs.solve("", "") == 0, "LCS both empty");
        check(Edit.solve("abc", "abc") == 0, "Edit identical");
        check(Edit.solve("", "abc") == 3, "Edit empty");
        check(Edit.solve("abc", "") == 3, "Edit reverse empty");
        check(Edit.solve("cat", "cut") == 1, "Substitution");
        check(Edit.solve("cat", "cats") == 1, "Insertion");
        check(Edit.solve("cats", "cat") == 1, "Deletion");
        check(Edit.solve("kitten", "sitting") == 3, "Edit normal");
        match("abcde", "ab", 0);
        match("abcde", "bc", 1);
        match("abcde", "de", 3);
        match("ababab", "ab", 0, 2, 4);
        match("aaaa", "aa", 0, 1, 2);
        match("abcde", "xyz");
        match("abc", "abcdef");
        match("abc", "", 0, 1, 2, 3);
        match("", "", 0);
        match("", "a");
        match("aaaaa", "aaa", 0, 1, 2);
        match("abc", "abc", 0);
        int[] sa = {5, 3, 1, 0, 4, 2};
        int[] lcp = {0, 1, 3, 0, 0, 2};
        check(Arrays.equals(Kasai.solve("banana", sa), lcp), "Kasai banana");
        check(Arrays.equals(Kasai.solve("", new int[0]), new int[0]), "Kasai empty");
        check(Arrays.equals(Kasai.solve("a", new int[]{0}), new int[]{0}), "Kasai one char");
        check(Arrays.equals(Kasai.solve("aaaa", new int[]{3, 2, 1, 0}), new int[]{0, 1, 2, 3}), "Kasai repeated");
        Random r = new Random(42);
        for (int t = 0; t < 1000; t++) {
            StringBuilder s = new StringBuilder();
            StringBuilder p = new StringBuilder();
            for (int i = 0, n = r.nextInt(30); i < n; i++) s.append("abcd".charAt(r.nextInt(4)));
            for (int i = 0, n = r.nextInt(10); i < n; i++) p.append("abcd".charAt(r.nextInt(4)));
            if (!Naive.search(s.toString(), p.toString()).equals(Kmp.search(s.toString(), p.toString())) ||
                    !Naive.search(s.toString(), p.toString()).equals(Rk.search(s.toString(), p.toString()))) {
                throw new RuntimeException("Random matching failed");
            }
        }
        check(true, "1000 random matching cases");
        System.out.println("All " + total + " tests passed");
    }
}
