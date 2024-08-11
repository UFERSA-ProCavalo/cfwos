package cfwos.model.list;

public interface InterfaceList<V> {
    // criterio - valor
    void addFirst(V value);

    void addLast(V value);

    boolean addAfter(V value, V crit);

    V removeFirst();

    V removeLast();

    V removeAfter(V crit);

    V peekFirst();

    V peekLast();

    V search(V crit);

    boolean isEmpty();

    void show();

    void showReverse();

    int getSize();
}
