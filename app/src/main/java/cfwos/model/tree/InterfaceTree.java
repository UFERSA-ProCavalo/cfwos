package cfwos.model.tree;

public interface InterfaceTree<K, V> {
    void Insert(K k, V v);

    void Remove(K k);

    V Search(K k);

    void Show();

    void ShowReverse();

    int getSize();

}
