package cfwos.model.cache;

public class AutoAdjustedList<K, V> implements InterfaceAutoAdjustedList<K, V> {

    private Node head;
    private Node tail;
    private int size;

    class Node {
        K key;
        V val;
        Node next;
        int frequency;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.next = null;
            this.frequency = 0;
        }
    }

    public AutoAdjustedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    //InsertLast
    public void insert(K key, V val) {
        Node newNode = new Node(key, val);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        newNode.frequency++;
    }

    @Override
    public V search(K key) {
        Node current = head;
        Node previous = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (previous != null) {
                    previous.next = current.next;
                    current.next = head;
                    head = current;
                }
                current.frequency++;
                return current.val;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    @Override
    public void update(K key, V val) {
        Node current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                current.val = val;
                return;
            }
            current = current.next;
        }
    }

    @Override
    public void remove(K key) {
        Node current = head;
        Node previous = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (previous != null) {
                    previous.next = current.next;
                } else {
                    head = current.next;
                }
                return;
            }
            previous = current;
            current = current.next;
        }
    }

    @Override
    //Remove Least Recently Used
    public void removeLRU() {
        Node current = head;
        Node previous = null;
        Node lru = head;
        Node previousLru = null;
        while (current != null) {
            if (current.frequency < lru.frequency) {
                lru = current;
                previousLru = previous;
            }
            previous = current;
            current = current.next;
        }
        if (previousLru != null) {
            previousLru.next = lru.next;
        } else {
            head = lru.next;
        }
    }

    @Override
    public void show() {
        Node current = head;
        while (current != null) {
            System.out.println("Key: " + current.key + " Value: " + current.val + " Frequency: " + current.frequency);
            current = current.next;
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(K key) {
        Node current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

}
