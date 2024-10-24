package cfwos.model.cache;

public interface InterfaceAutoAdjustedList<K, V> {

    void insert(K key, V val);

    V search(K key);

    void update(K key, V val);

    void remove(K key);

    void removeLRU();

    void show();

    int getSize();

    boolean isEmpty();

}
