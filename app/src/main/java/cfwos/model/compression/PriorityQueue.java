package cfwos.model.compression;

public class PriorityQueue<T extends Comparable<T>> {
    private MinHeap<T> minHeap;

    public PriorityQueue(int capacity) {
        minHeap = new MinHeap<>(capacity);
    }

    public void insert(T item) {
        minHeap.insert(item);
    }

    public T remove() {
        return minHeap.remove();
    }

    public T getMin() {
        return minHeap.getMin();
    }

    public int getSize() {
        return minHeap.getSize();
    }
}