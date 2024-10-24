package cfwos.model.cache;

public class CacheLRU<K, V> {

    private static final int MAX_SIZE = 30;
    private AutoAdjustedList<K, V> list;

    public CacheLRU() {
        list = new AutoAdjustedList<>();
    }

    public void add(K key, V val) {
        if (list.getSize() == MAX_SIZE)
            list.removeLRU();
        list.insert(key, val);
    }

    public void remove(K key) {
        list.remove(key);
    }

    public void update(K key, V val) {
        list.update(key, val);
    }

    public V get(K key) {
        return list.search(key);
    }

    public void showCache() {
        list.show();
    }

    public int getSize() {
        return list.getSize();
    }

    public boolean isInCache(K key) {
        return list.contains(key);
    }



    // public boolean isEmpty() {
    // return list.isEmpty();

}
