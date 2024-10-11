package cfwos.model.hash;

public interface InterfaceHashTableLinear<K, V> {

    void insert(K key, V val);

    V search(K key);

    void remove(K key);

    void show();

}
