package cfwos.model.cache;

public class CacheRANDOM<K, V> {
    private static final int MAX_SIZE = 20;
    private HashTableLinear<K, V> cache;

    public CacheRANDOM() {
        cache = new HashTableLinear<>(MAX_SIZE);
    }

    public void add(K key, V val) {
        if (cache.getSize() == MAX_SIZE)
            cache.insert_after_collision(cache.removeRandom(), key, val);
        else
            cache.insert(key, val);
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void update(K key, V val) {
        cache.update(key, val);
    }

    public V get(K key) {
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
