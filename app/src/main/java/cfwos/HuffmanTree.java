    package cfwos;

    public class HuffmanTree {
        class Node implements Comparable<Node> {
            private char character;
            private int frequency;
            private Node left;
            private Node right;

            @Override
            public int compareTo(Node o) {
                return this.frequency - o.frequency;
            }
        }

        private Node root;

        public void createTree(int n, char[] characters, int[] frequencies) {
            PriorityQueue<Node> minHeap = new PriorityQueue<>(n);
            for (int i = 0; i < n; i++) {
                Node node = new Node();
                node.character = characters[i];
                node.frequency = frequencies[i];
                node.left = null;
                node.right = null;
                minHeap.insert(node);
            }

            root = null;

            while (minHeap.getSize() > 1) {
                Node x = minHeap.remove();
                Node y = minHeap.remove();
                Node z = new Node();
                z.frequency = x.frequency + y.frequency;
                z.character = '-';
                z.left = x;
                z.right = y;
                minHeap.insert(z);
            }

            root = minHeap.remove();
        }

        void printCode(Node node, String s) {
            if (node.left == null && node.right == null && Character.isLetter(node.character)) {
                System.out.println(node.character + ":" + s);
                return;
            }
            printCode(node.left, s + "0");
            printCode(node.right, s + "1");
        }

        void printCodes() {
            printCode(root, "");
        }

        public String compress(String message, String[] huffmanCodes) {
            StringBuilder compressed = new StringBuilder();
            for (char c : message.toCharArray()) {
                compressed.append(huffmanCodes[c]);
            }
            return compressed.toString();
        }

        public String decompress(String compressedMessage) {
            StringBuilder decompressed = new StringBuilder();
            Node current = root;
            for (char bit : compressedMessage.toCharArray()) {
                if (bit == '0') {
                    current = current.left;
                } else {
                    current = current.right;
                }
                if (current.left == null && current.right == null) {
                    decompressed.append(current.character);
                    current = root;
                }
            }
            return decompressed.toString();
        }

        private void generateCodesRecursive(Node node, String code, String[] huffmanCodes) {
            if (node == null) {
                return;
            }
            if (node.left == null && node.right == null) {
                huffmanCodes[node.character] = code;
                return;
            }
            generateCodesRecursive(node.left, code + "0", huffmanCodes);
            generateCodesRecursive(node.right, code + "1", huffmanCodes);
        }

        public String[] generateHuffmanCodes() {
            String[] huffmanCodes = new String[256]; // Assume ASCII
            generateCodesRecursive(root, "", huffmanCodes);
            return huffmanCodes;
        }
    }