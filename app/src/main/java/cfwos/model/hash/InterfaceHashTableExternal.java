package cfwos.model.hash;

public interface InterfaceHashTableExternal<K, V> {

    void insert(K key, V value);

    V search(K key);

    void remove(K key);

    void show();

}
