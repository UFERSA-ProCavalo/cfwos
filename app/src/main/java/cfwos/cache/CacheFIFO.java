package cfwos.cache;

import cfwos.model.list.LinkedList;

public class CacheFIFO<V> {
    private static final int MAX_SIZE = 20;
    private LinkedList<V> cache;

    public CacheFIFO() {
        cache = new LinkedList<>();
    }

    public void add(V value) {
        if (cache.getSize() == MAX_SIZE) {
            cache.removeFirst();
        }
        cache.addLast(value);
    }

    public void remove(V value) {
        cache.removeCrit(value);
        //System.out.println(cache.getSize());

    }

    public V get(V value) {
        return cache.search(value);
    }

    // public V searchByCode(V value) {
    //     LinkedList<V>.Node temp = cache.head;
    // }

    public void showCache() {
        cache.show();
    }

    public int getSize() {
        return cache.getSize();
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

}
