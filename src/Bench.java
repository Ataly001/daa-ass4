import java.util.*;
import java.io.*;

public class Bench {
    static Random r = new Random(42);
    static int[] sizes = {100, 500, 1000};
    static int[] texts = {10000, 50000, 100000};
    static int[] ks = {1000, 5000, 10000};

    static String gen(int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) s.append("abcd".charAt(r.nextInt(4)));
        return s.toString();
    }

    static double time(Runnable f) {
        for (int i = 0; i < 3; i++) f.run();
        long sum = 0;
        for (int i = 0; i < 5; i++) {
            long a = System.nanoTime();
            f.run();
            sum += System.nanoTime() - a;
        }
        return sum / 5.0 / 1000000.0;
    }

    static int[] suffix(String s) {
        int n = s.length();
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = i;
        Arrays.sort(a, (x, y) -> {
            int i = x;
            int j = y;
            while (i < n && j < n) {
                if (s.charAt(i) != s.charAt(j)) return Character.compare(s.charAt(i), s.charAt(j));
                i++;
                j++;
            }
            return Integer.compare(n - x, n - y);
        });
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = a[i];
        return sa;
    }

    static void dp() throws Exception {
        try (PrintWriter w = new PrintWriter("results/dp.csv")) {
            w.println("size,lcs,edit");
            for (int n : sizes) {
                String a = gen(n);
                String b = gen(n);
                double x = time(() -> Lcs.solve(a, b));
                double y = time(() -> Edit.solve(a, b));
                w.println(n + "," + x + "," + y);
                System.out.println("DP " + n + " done");
            }
        }
    }

    static void match() throws Exception {
        try (PrintWriter w = new PrintWriter("results/match.csv")) {
            w.println("size,case,algorithm,ms,comparisons");
            for (int n : texts) {
                String s = gen(n);
                String p = s.substring(n / 2, n / 2 + 20);
                String q = "zzzzzzzzzzzzzzzzzzzz";
                for (int c = 0; c < 2; c++) {
                    String pat = c == 0 ? p : q;
                    String name = c == 0 ? "present" : "absent";
                    ArrayList<Integer> a = Naive.search(s, pat);
                    ArrayList<Integer> b = Kmp.search(s, pat);
                    ArrayList<Integer> d = Rk.search(s, pat);
                    if (!a.equals(b) || !a.equals(d)) throw new RuntimeException("Matching error");
                    double x = time(() -> Naive.search(s, pat));
                    long cx = Naive.cnt;
                    double y = time(() -> Kmp.search(s, pat));
                    long cy = Kmp.cnt;
                    double z = time(() -> Rk.search(s, pat));
                    long cz = Rk.cnt;
                    w.println(n + "," + name + ",Naive," + x + "," + cx);
                    w.println(n + "," + name + ",KMP," + y + "," + cy);
                    w.println(n + "," + name + ",RK," + z + "," + cz);
                }
                System.out.println("Matching " + n + " done");
            }
        }
    }

    static void kasai() throws Exception {
        try (PrintWriter w = new PrintWriter("results/kasai.csv")) {
            w.println("size,ms");
            for (int n : ks) {
                String s = gen(n);
                int[] sa = suffix(s);
                double t = time(() -> Kasai.solve(s, sa));
                w.println(n + "," + t);
                System.out.println("Kasai " + n + " done");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new File("results").mkdirs();
        dp();
        match();
        kasai();
        System.out.println("Experiments completed");
    }
}
