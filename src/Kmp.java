import java.util.ArrayList;
public class Kmp {
    public static long cnt;

    public static ArrayList<Integer> search(String s, String p) {
        ArrayList<Integer> res = new ArrayList<>();
        int n = s.length();
        int m = p.length();
        cnt = 0;

        if (m == 0) {
            for (int i = 0; i <= n; i++) {
                res.add(i);
            }
            return res;
        }

        int[] lps = new int[m];
        int j = 0;

        for (int i = 1; i < m;) {
            cnt++;
            if (p.charAt(i) == p.charAt(j)) {
                lps[i++] = ++j;
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }

        int i = 0;
        j = 0;

        while (i < n) {
            cnt++;
            if (s.charAt(i) == p.charAt(j)) {
                i++;
                j++;

                if (j == m) {
                    res.add(i - m);
                    j = lps[j - 1];
                }
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return res;
    }
}
