import java.util.ArrayList;

public class Rk {
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

        if (m > n) {
            return res;
        }

        long mod = 1000000007L;
        long base = 256;
        long pow = 1;
        long a = 0;
        long b = 0;

        for (int i = 0; i < m; i++) {
            a = (a * base + p.charAt(i)) % mod;
            b = (b * base + s.charAt(i)) % mod;

            if (i < m - 1) {
                pow = (pow * base) % mod;
            }
        }

        for (int i = 0; i <= n - m; i++) {
            if (a == b) {
                int j = 0;

                while (j < m) {
                    cnt++;
                    if (s.charAt(i + j) != p.charAt(j)) {
                        break;
                    }
                    j++;
                }

                if (j == m) {
                    res.add(i);
                }
            }

            if (i < n - m) {
                b = (b - s.charAt(i) * pow % mod + mod) % mod;
                b = (b * base + s.charAt(i + m)) % mod;
            }
        }
        return res;
    }
}
