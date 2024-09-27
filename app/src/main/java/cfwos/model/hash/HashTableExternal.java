package cfwos.model.hash;

import java.util.LinkedList;

public class HashTableExternal<K, V> implements InterfaceHashTableExternal<K, V> {

    private int M;
    private int size;
    private LinkedList<Node<K, V>>[] table;
    int collisionCounter = 0;
    String collisionMessage = "";

    // internal Node class
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
        this.table = (LinkedList<Node<K, V>>[]) new LinkedList[M]; // Correctly type the array
        for (int i = 0; i < M; i++) {
            table[i] = new LinkedList<>(); // Initialize each bucket
        }
        this.size = 0;
    }

    // Hash function
    int hash(K key, int tableSize) {
        float A = 0.6180339887f; // Golden ratio
        float temp = (float) key.hashCode() * A; 
        temp = temp - (int) temp;
        return (int) (tableSize * temp);
        
    }

    // reescale
    @SuppressWarnings("unchecked")
    private void resize() {
        int newSize = M * 2; // Double the size of the table
        LinkedList<Node<K, V>>[] newTable = (LinkedList<Node<K, V>>[]) new LinkedList[newSize];

        for (int i = 0; i < newSize; i++) {
            newTable[i] = new LinkedList<>(); // Initialize the new table
        }

        // Re-hash all elements into the new table
        for (LinkedList<Node<K, V>> bucket : table) {
            for (Node<K, V> node : bucket) {
                int newIndex = hash(node.key, newSize);
                newTable[newIndex].add(new Node<>(node.key, node.value));
            }
        }

        // Replace the old table with the new table
        this.table = newTable;
        this.M = newSize; // Update the table size
    }

    // Insert key and value
    public void insert(K key, V value) {
        if (size >= M * 0.75) {
            resize();
        }
        int index = hash(key, M);
        LinkedList<Node<K, V>> bucket = table[index];

        for (Node<K, V> node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        bucket.add(new Node<>(key, value));
        size++;

        if (bucket.size() > 1) {
            collisionCounter = collisionCounter + 1;
            collisionMessage = "Collision at { index " + index + " with key -> " + key + "}";
        }
    }

    // Buscar valor por chave
    public V search(K key) {
        int index = hash(key, M);
        LinkedList<Node<K, V>> bucket = table[index];

        for (Node<K, V> node : bucket) {
            if (node.key.equals(key)) {
                //System.out.println("Found key: " + key + " with value: " + node.value);
                return node.value;
            }
        }

        return null;
    }

    // Remove key
    public void remove(K key) {
        int index = hash(key, M);
        LinkedList<Node<K, V>> bucket = table[index];

        bucket.removeIf(node -> node.key.equals(key));
    }

    // show table
    public void show() {
        for (int i = 0; i < M; i++) {
            System.out.print("[" + i + "] -> ");

            if (table[i].isEmpty()) {
                System.out.println("Empty\n");
            } else {
                for (Node<K, V> node : table[i]) {
                    System.out.print("{ " + node.key + " } -> ");
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
        collisionCounter = 0;
        return null;

    }
}