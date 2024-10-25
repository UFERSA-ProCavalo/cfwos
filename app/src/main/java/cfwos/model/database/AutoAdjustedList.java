package cfwos.model.database;

public class AutoAdjustedList<K, V> {

    Node<K, V> head;
    private int size;

    // Internal Node class
    public static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public AutoAdjustedList() {
        this.head = null;
        this.size = 0;
    }

    public void insert(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public V search(K key) {
        Node<K, V> current = head;
        Node<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous != null) {
                    previous.next = current.next;
                    current.next = head;
                    head = current;
                }
                return current.value;
            }
            previous = current;
            current = current.next;
        }

        return null;
    }

    public boolean remove(K key) {
        Node<K, V> current = head;
        Node<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous != null) {
                    previous.next = current.next;
                } else {
                    head = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    // Get size of the list
    public int getSize() {
        return size;
    }

    // Check if list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Check if list contains a key
    public boolean contains(K key) {
        Node<K, V> current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void show() {
        Node<K, V> current = head;
        while (current != null) {
            System.out.println("Key: " + current.key + ", Value: " + current.value);
            current = current.next;
        }
    }
}
