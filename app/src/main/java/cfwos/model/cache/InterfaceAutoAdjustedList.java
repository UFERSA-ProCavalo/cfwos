package cfwos.model.cache;

public interface InterfaceAutoAdjustedList<K, V> {

    void insert(K key, V val);

    V search(K key);

    void update(K key, V val);

    boolean remove(K key);

    void removeLRU();

    int getSize();

    boolean isEmpty();

}
