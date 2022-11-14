package Problem3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Integer> a;
    public static StringBuilder ans;

    public static void dfs(int x) {
        if (x > a.size())
            return;
        dfs(x * 2);
        dfs(x * 2 + 1);
        ans.append(a.get(x - 1)).append(",");
    }

    public static void main(String[] args) {
        a = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        ans = new StringBuilder();
        while (scanner.hasNextInt()) {
            a.add(scanner.nextInt());
        }
        long var1 = 0;
        for (var i : a) {
            var1 += i;
        }
        var1 /= a.size();
        ans.append(var1).append("#");
        List<Integer> var2 = new ArrayList<>(a);
        var2.sort(Comparator.naturalOrder());
        for (var i : var2) {
            ans.append(i).append(",");
        }
        ans.deleteCharAt(ans.length() - 1).append("#");
        dfs(1);
        ans.deleteCharAt(ans.length() - 1);
        System.out.println(ans);
    }
}
