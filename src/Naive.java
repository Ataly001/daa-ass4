import java.util.ArrayList;
public class Naive {
    public static long cnt;

    public static ArrayList<Integer> search(String s, String p) {
        ArrayList<Integer> res = new ArrayList<>();
        int n = s.length();
        int m = p.length();
        cnt = 0;

        for (int i = 0; i <= n - m; i++) {
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

        return res;
    }
}
