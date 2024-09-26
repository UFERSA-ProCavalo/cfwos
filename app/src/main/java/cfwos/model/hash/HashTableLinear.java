package cfwos.model.hash;

import java.util.Random;

public class HashTableLinear<K> implements InterfaceHashTableLinear<K> {

    int M;
    int size;
    K[] table;
    boolean[] deleted;
    Random random;
    int collisionCounter = 0;
    String collisionMessage = "";

    // Constructor
    @SuppressWarnings("unchecked")
    public HashTableLinear(int size) {
        this.M = size;
        this.table = (K[]) new Object[M];
        this.deleted = new boolean[M];
        this.size = 0;
        this.random = new Random();

    }

    // Hash function
    // the hashCode() method returns a hash code value for the object since it's generic
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
    public void insert(K key) {
        int k = hash(key);
        //int index = k; // Save the initial index to prevent infinite loop
        //int attempts = 0;

        // Tentativa linear
        while (table[k] != null && !deleted[k]) {
            if (table[k].equals(key)) {
                return;
            }
            System.out.println("Collision at { index " + k + " with key -> " + key.hashCode() + "} ");
            k = (k + 1) % M;
            
                // collisionCounter = collisionCounter + 1;
                // collisionMessage+= "Collision at { index " + table[k] + " with key -> " + key + "} ";
        }
        System.out.println("Inserted key at index: " + k);

        table[k] = key;
        deleted[k] = false;
        size++;
    }

    // Search key
    @Override
    public K search(K key) {
        int k = hash(key);
        //int index = k; // Save the initial index to prevent infinite loop
        int attempts = 0;

        // Tentativa linear
        while (table[k] != null) {
            if (table[k].equals(key)) {
                return table[k];
            }
            k = (k + 1) % M;

            attempts++;
            if (attempts >= M) {
                return null;
            }
        }
        
        return null;
    }

    // Remove key
    @Override
    public void remove(K key) {
        int k = hash(key);

        // Tentativa linear
        while (table[k] != null) {
            if (table[k].equals(key)) {
                table[k] = null;
                deleted[k] = true;
                size--;
                return;
            }
            k = (k + 1) % M;
        }
    }

    //Remove random key
    public void removeRandom(){
        int k = random();

        // Tentativa aleatória
        while (table[k] == null || deleted[k]) {
            k = random();
        }

        table[k] = null;
        deleted[k] = true;
        size--;

        System.out.println("Removed key at index: " + k);
    }

    // Update key
    public void update(K key) {
        int k = hash(key);

        // Tentativa linear
        while (table[k] != null) {
            if (table[k].equals(key)) {
                return;
            }
            k = (k + 1) % M;
        }

        table[k] = key;
        deleted[k] = false;
        size++;
    }

    // Show table
    public void show() {
        for (int i = 0; i < M; i++) {
            if (table[i] != null && !deleted[i]) {
                System.out.println("[" + i + "] -> " + table[i]);
            } else if (deleted[i]) {
                System.out.println("[" + i + "] -> DELETED");
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
