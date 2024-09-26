package cfwos.model.hash;

public interface InterfaceHashTableLinear<K> {

    void insert(K key);

    K search(K key);

    void remove(K key);

    void show();

}
