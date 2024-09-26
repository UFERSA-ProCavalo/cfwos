package cfwos.cache;

import cfwos.model.hash.HashTableLinear;

public class CacheRANDOM<K> {
    private static final int MAX_SIZE = 20;
    private HashTableLinear<K> cache;

    public CacheRANDOM() {
        cache = new HashTableLinear<>(MAX_SIZE);
    }

    public void add(K key) {
        if (cache.getSize() == MAX_SIZE) {
            cache.removeRandom();
        }
        cache.insert(key);
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void update(K key) {
        cache.update(key);
    }

    public K get(K key) {
        return cache.search(key);
    }

    public void showCache() {
        cache.show();
    }

    public int getSize() {
        return cache.getSize();
    }

    // public boolean isEmpty() {
    // return cache.isEmpty();
    // }

}
