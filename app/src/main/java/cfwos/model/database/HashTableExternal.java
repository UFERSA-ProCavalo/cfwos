package cfwos.model.database;

public class HashTableExternal<K, V> implements InterfaceHashTableExternal<K, V> {

    private int M;
    private int size;
    private AutoAdjustedList<K, V>[] table;
    int collisionCounter = 0;
    String collisionMessage = "";

    static class Node<K, V> {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Constructor
    @SuppressWarnings("unchecked")
    public HashTableExternal(int size) {
        this.M = size;
        this.table = (AutoAdjustedList<K, V>[]) new AutoAdjustedList[M];
        for (int i = 0; i < M; i++) {
            table[i] = new AutoAdjustedList<>();
        }
        this.size = 0;
    }

    private int hash(K key, int tableSize) {
        float A = 0.6180339887f;
        float temp = (float) key.hashCode() * A;
        temp = temp - (int) temp;
        return (int) (tableSize * temp);
    }

    private void ExamineLoad() {

        double loadFactor = (double) size / M;

        if (loadFactor >= 0.85) {
            resize(M * 2);
        } else {
            if (loadFactor < 0.25) {
                resize(bigger(M / 2, 7));
            }
        }
    }

    private int bigger(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        AutoAdjustedList<K, V>[] newTable = (AutoAdjustedList<K, V>[]) new AutoAdjustedList[newSize];

        for (int i = 0; i < newSize; i++) {
            newTable[i] = new AutoAdjustedList<>();
        }

        for (int i = 0; i < M; i++) {
            AutoAdjustedList<K, V> bucket = table[i];
            AutoAdjustedList.Node<K, V> current = bucket.head;
            while (current != null) {
                int newIndex = hash(current.key, newSize);
                newTable[newIndex].insert(current.key, current.value);
                current = current.next;
            }
        }

        this.table = newTable;
        this.M = newSize;
    }

    public void insert(K key, V value) {

        ExamineLoad();

        int index = hash(key, M);
        AutoAdjustedList<K, V> bucket = table[index];

        if (bucket.contains(key)) {
            bucket.remove(key);
        }

        bucket.insert(key, value);
        size++;

        if (bucket.getSize() > 1) {
            collisionCounter++;
            collisionMessage = "Collision at { index " + index + " with key -> " + key + " }";
        }
    }

    public V search(K key) {
        int index = hash(key, M);
        AutoAdjustedList<K, V> bucket = table[index];

        return bucket.search(key);
    }

    public void remove(K key) {
        int index = hash(key, M);
        AutoAdjustedList<K, V> bucket = table[index];

        if (bucket.remove(key)) {
            size--;
        }
    }

    public void show() {
        for (int i = 0; i < M; i++) {
            System.out.print("[" + i + "] -> ");

            if (table[i].isEmpty()) {
                System.out.println("Empty\n");
            } else {
                for (AutoAdjustedList.Node<K, V> current = table[i].head; current != null; current = current.next) {
                    System.out.print("{ " + current.key + " } -> ");
                }
                System.out.println("\n");
            }
        }
    }

    public int getSize() {
        return size;
    }

    public String getCollision() {
        if (collisionCounter != 0) {
            return collisionMessage;
        }
        return null;
    }

    // Check if the table contains a key
    public boolean Contains(K key) {
        int index = hash(key, M);
        AutoAdjustedList<K, V> bucket = table[index];

        return bucket.contains(key);
    }
}
