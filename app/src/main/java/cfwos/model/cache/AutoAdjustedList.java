package cfwos.model.cache;

public class AutoAdjustedList<K, V> implements InterfaceAutoAdjustedList<K, V> {

    public Node head;
    public Node tail;
    public int size;

    public class Node {
        public K key;
        public V val;
        public Node next;
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
    public void insert(K key, V val) {
        Node newNode = new Node(key, val);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = null;
        }
        size++;
        if (newNode.frequency == 0) {
            newNode.frequency++;

        }
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
    public boolean remove(K key) {
        Node current = head;
        Node previous = null;
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

    @Override
    public void removeLRU() {
        if (head == null) {
            return; // Lista está vazia, nada para remover
        }

        Node current = head;
        Node previous = null;

        // Encontrar o nó com a menor frequência (LRU)
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

        // Remover o nó LRU (o primeiro com a menor frequência encontrado)
        if (previousLru != null) {
            previousLru.next = lru.next; // Remove o LRU ao desconectar o nó
        } else {
            head = lru.next; // Caso o LRU seja o head
        }

        if (lru == tail) {
            tail = previousLru; // Caso o LRU seja o tail
        }

        size--;
    }

    public void showCache() {
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

    public int setSize(int size) {
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
