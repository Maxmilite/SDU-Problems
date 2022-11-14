package Problem7;

public class MyHashMap {
    public static class Entry {
        public String key, value;
        Entry next;

        public Entry(String key, String value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public final static int DEFAULT_CAPACITY = 16;
    public final static float DEFAULT_LOAD_FACTOR = 0.75f;

    public int capacity;
    public float loadFactor;
    public int size = 0;

    public Entry[] table;

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public int calc(int x) {
        int res = 1;
        while (res <= x)
            res <<= 1;
        return res;
    }

    public MyHashMap(int capacity, float loadFactor) {
        this.capacity = calc(capacity);
        this.loadFactor = loadFactor;
        this.table = new Entry[capacity];
    }

    public String put(String key, String value) {
        int index = key.hashCode() % table.length;
        Entry current = table[index];
        if (current != null) {
            while (current != null) {
                if (current.key.equals(key)) {
                    String oldValue = current.value;
                    current.value = value;
                    return oldValue;
                }
                current = current.next;
            }
            table[index] = new Entry(key, value, table[index]);
            size++;
            return null;
        }
        table[index] = new Entry(key, value, null);
        size++;
        return null;
    }

    public String get(String key) {
        int index = key.hashCode() % table.length;
        Entry current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (int i = 0; i < table.length; ++i) {
            if (table[i] == null)
                continue;
            s.append(table[i].key).append(":").append(table[i].value).append(",");
        }
        s.deleteCharAt(s.length() - 1).append("]");
        return s.toString();
    }
}
