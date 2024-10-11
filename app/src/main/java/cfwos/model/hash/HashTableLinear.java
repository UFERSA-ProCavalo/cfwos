package cfwos.model.hash;

import java.util.Random;

public class HashTableLinear<K, V> implements InterfaceHashTableLinear<K, V> {

    int M;
    int size;
    Random random;
    int collisionCounter = 0;
    String collisionMessage = "";
    Node<K, V>[] table;
    // internal Node class
    static class Node<K, V> {
        K key;
        V val;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    // Constructor
    @SuppressWarnings("unchecked")
    public HashTableLinear(int size) {
        this.M = size;
        this.table = new Node[M];
        this.size = 0;
        this.random = new Random();

    }

    // Hash function
    // the hashCode() method returns a hash code value for the object since it's
    // generic
    int hash(K key) {
        float A = 0.6180339887f; // Golden ratio
        float temp = (float) key.hashCode() * A;
        temp = temp - (int) temp;
        return (int) (M * temp);
    }

    //
    public int random() {
        return random.nextInt(M);
    }

    // Insert key
    @Override
    public void insert(K key, V val) {
        // int k = hash(key);
        int h = random();
        Node<K, V> node = table[h];

        // Tentativa linear
        while ((table[h] != null)) {
            if (node.key.equals(key)) {
                return;
            }
            System.out.println("Collision at { index " + h + " with key -> " + key.hashCode() + "} ");
            h = (h + 1) % M;
            
            // collisionCounter = collisionCounter + 1;
            // collisionMessage+= "Collision at { index " + table[k] + " with key -> " + key
            // + "} ";
        }
        System.out.println("Inserted key " + key + " at index: " + h);
        node = new Node<>(key, val);
        table[h] = node;
        size++;
    }

    public void insert_after_collision(int h, K key, V val) {
        System.out.println("Inserted key " + key + " at index: " + h);
        Node<K, V> node = new Node<>(key, val);
        table[h] = node;
        size++;
    }

    // Search key
    @Override
    public V search(K key) {
        int h = hash(key);
        Node<K, V> node =  table[h];
        // int index = k; // Save the initial index to prevent infinite loop
        int attempts = 0;

        // Tentativa linear
        while (attempts < M) {
            if ((node != null) && (node.key.equals(key))) {
                return node.val;
            }
            h = (h + 1) % M;
            attempts++;
        }

        return null;
    }

    // Remove key
    @Override
    public void remove(K key) {
        int k = hash(key);
        V removed = search(key);
        if (removed != null) {
            table[k].val = null;
            size--;
            System.out.println("Removed key at index: " + removed);
        }
        return;
    }

    // Remove random key
    public int removeRandom() {
        int h = random();

        // Tentativa aleatória
        while (table[h] == null) {
            h = random();
        }

        table[h] = null;
        size--;

        System.out.println("Removed key at index: " + h);
        return h;
    }

    // Update key
    public void update(K key, V val) {
        int k = hash(key);

        // Tentativa linear
        while (table[k] != null) {
            if (table[k].equals(key)) {
                return;
            }
            k = (k + 1) % M;
        }

        table[k].val = val;
        size++;
    }

    // Show table
    public void show() {
        for (int i = 0; i < M; i++) {
            if (table[i] != null) {
                System.out.println("[" + i + "] -> " + table[i].val);
            } else {
                System.out.println("[" + i + "] -> null");
            }
        }
    }

    // Get size
    public int getSize() {
        return size;
    }

    // Is full
    public boolean isFull() {
        return size == M;
    }

}
