import java.io.*;
import java.util.*;

public class Main {
    public static class Vec2d {
        public int x, y;

        public Vec2d(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new int[] {x, y});
        }

        public boolean equals(Vec2d vec2d) {
            return this.x == vec2d.x && this.y == vec2d.y;
        }
        @Override
        public boolean equals(Object vec2d) {
            if (!(vec2d instanceof Vec2d))
                return false;
            return this.x == ((Vec2d) vec2d).x && this.y == ((Vec2d) vec2d).y;
        }
    }

    public static class Vec3d {
        public int x, y, z;

        public Vec3d(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        @Override
        public int hashCode() {
            return Arrays.hashCode(new int[] {x, y, z});
        }
        public boolean equals(Vec3d vec2d) {
            return this.x == vec2d.x && this.y == vec2d.y && this.z == vec2d.z;
        }
        @Override
        public boolean equals(Object vec2d) {
            if (!(vec2d instanceof Vec3d))
                return false;
            return this.x == ((Vec3d) vec2d).x && this.y == ((Vec3d) vec2d).y && this.z == ((Vec3d) vec2d).z;
        }
    }

    public static class Scanner {
        public BufferedReader in;
        public StringTokenizer tok;

        public String next() {
            hasNext();
            return tok.nextToken();
        }

        public String nextLine() {
            try {
                return in.readLine();
            } catch (Exception e) {
                return null;
            }
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        public boolean hasNext() {
            while (tok == null || !tok.hasMoreTokens()) try {
                tok = new StringTokenizer(in.readLine());
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        public Scanner(InputStream inputStream) {
            in = new BufferedReader(new InputStreamReader(inputStream));
        }
    }

    public static Map<Vec3d, Boolean> isblock = new HashMap<>();
    public static Map<Vec2d, Boolean> isused = new HashMap<>();
    public static Map<Vec2d, Integer> dist = new HashMap<>();
    public static Map<Vec2d, Boolean> iswater = new HashMap<>();
    public static Map<Vec2d, Integer> strwater = new HashMap<>();


    public static final int[] dx = {1, -1, 0, 0}, dy = {0, 0, 1, -1};

    public static int n, k, _x0, _y0;
    public static int[] x = new int[100050], y = new int[100050], z = new int[100050];
    public static List<Integer> var_id = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        k = scanner.nextInt();
        _x0 = scanner.nextInt();
        _y0 = scanner.nextInt();
        iswater.put(new Vec2d(_x0, _y0), true);
        for (int i = 1; i <= n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
            z[i] = scanner.nextInt();
            isblock.put(new Vec3d(x[i], y[i], z[i]), true);
            var_id.add(i);
        }
        var_id.sort((x, y) -> z[y] - z[x]);
        List<Integer> id = new ArrayList<>();
        id.add(0);
        for (int i = 0; i < n; ++i)
            id.add(var_id.get(i));
        for (int i = 0; i < 5; ++i)
            id.add(0);
        for (int i = 1; i <= n; i++) {
            int height = z[id.get(i)];
            Queue<Vec2d> p = new LinkedList<>(), q = new LinkedList<>();
            // spread at the same height
            for (int nid = id.get(i); i <= n && z[nid] == height; ) {
                int nx = x[nid], ny = y[nid];
                Vec2d u = new Vec2d(nx, ny);
                if (iswater.getOrDefault(u, false)) {
                    iswater.put(u, false);
                    p.add(u);
                    strwater.put(u, k);
                }
                for (int j = 0; j < 4; j++) {
                    int nx1 = nx + dx[j], ny1 = ny + dy[j];
                    Vec2d v = new Vec2d(nx1, ny1);
                    Vec3d v1 = new Vec3d(nx1, ny1, height);
                    Vec3d v2 = new Vec3d(nx1, ny1, height + 1);
                    if (!isused.getOrDefault(v, false) && !isblock.getOrDefault(v1, false) && !isblock.getOrDefault(v2, false)) {
                        isused.put(v, true);
                        dist.put(v, 0);
                        q.add(v);
                    }
                }
                i++;
                nid = id.get(i);
            }
            i--;
            // spread water in Q
            while (!q.isEmpty()) {
                Vec2d var1 = q.element();
                q.remove();
                int x = var1.x, y = var1.y;
                Vec2d u = new Vec2d(x, y);
                for (int j = 0; j < 4; j++) {
                    int nx = x + dx[j], ny = y + dy[j];
                    Vec2d v = new Vec2d(nx, ny);
                    Vec3d v1 = new Vec3d(nx, ny, height);
                    Vec3d v2 = new Vec3d(nx, ny, height + 1);
                    if (dist.getOrDefault(v, 0) == 0 && isblock.getOrDefault(v1, false) && !isblock.getOrDefault(v2, false)) {
                        dist.put(v, dist.get(u) + 1);
                        q.add(v);
                    }
                }
            }
            //spread water in P
            while (!p.isEmpty()) {
                Vec2d var1 = p.element();
                p.remove();
                int x = var1.x, y = var1.y;
                Vec2d u = new Vec2d(x, y);
                Vec3d u1 = new Vec3d(x, y, height);
                int d = dist.getOrDefault(u, 0), s = strwater.getOrDefault(u, 0);
                if (!isblock.getOrDefault(u1, false)) {
                    iswater.put(u, true);
                    continue;
                }
                if (s == 1)
                    continue;
                for (int j = 0; j < 4; j++) {
                    int nx = x + dx[j], ny = y + dy[j];
                    Vec2d v = new Vec2d(nx, ny);
                    Vec3d v1 = new Vec3d(nx, ny, height + 1);
                    if (dist.getOrDefault(v, 0) == d - 1 && strwater.getOrDefault(v, 0) == 0 && !isblock.getOrDefault(v1, false)) {
                        strwater.put(v, s - 1);
                        p.add(v);
                    }
                }
            }
            isused.clear();
            dist.clear();
            strwater.clear();
        }
        int cnt = 0;
        for (boolean i : iswater.values()) {
            cnt += i ? 1 : 0;
        }
        System.out.println(cnt);
    }
}
