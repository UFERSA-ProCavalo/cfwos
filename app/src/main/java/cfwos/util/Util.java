package cfwos.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cfwos.HuffmanTree;
import cfwos.model.WorkOrder;

public class Util {
    public static class FrequencyTable {
        private char[] characters;
        private int[] frequencies;
        private int size;

        public FrequencyTable(int capacity) {
            characters = new char[capacity];
            frequencies = new int[capacity];
            size = 0;
        }

        public void add(char character) {
            for (int i = 0; i < size; i++) {
                if (characters[i] == character) {
                    frequencies[i]++;
                    return;
                }
            }
            characters[size] = character;
            frequencies[size] = 1;
            size++;
        }

        public char[] getCharacters() {
            char[] result = new char[size];
            System.arraycopy(characters, 0, result, 0, size);
            return result;
        }

        public int[] getFrequencies() {
            int[] result = new int[size];
            System.arraycopy(frequencies, 0, result, 0, size);
            return result;
        }

        public int getSize() {
            return size;
        }

    }

    public static byte[] serializeWorkOrder(WorkOrder workOrder) throws IOException {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutStream = new ObjectOutputStream(byteOutStream);
        objectOutStream.writeObject(workOrder);
        objectOutStream.flush();
        return byteOutStream.toByteArray();
    }

    public static WorkOrder deserializeWorkOrder(byte[] workOrderBytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteInStream = new ByteArrayInputStream(workOrderBytes);
        ObjectInputStream objectInStream = new ObjectInputStream(byteInStream);
        return (WorkOrder) objectInStream.readObject();
    }

    public static String compressWorkOrder(byte[] workOrderBytes, HuffmanTree huffmanTree) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : workOrderBytes) {
            stringBuilder.append((char) b);
        }

        Util.FrequencyTable frequencyTable = new Util.FrequencyTable(256);
        for (char c : stringBuilder.toString().toCharArray()) {
            frequencyTable.add(c);
        }

        huffmanTree.createTree(frequencyTable.getSize(), frequencyTable.getCharacters(),
                frequencyTable.getFrequencies());
        String[] huffmanCodes = huffmanTree.generateHuffmanCodes();

        return huffmanTree.compress(stringBuilder.toString(), huffmanCodes);
    }

    public static byte[] decompressWorkOrder(String compressedMessage, HuffmanTree huffmanTree) {
        String decompressedString = huffmanTree.decompress(compressedMessage);

        byte[] decompressedBytes = new byte[decompressedString.length()];
        for (int i = 0; i < decompressedString.length(); i++) {
            decompressedBytes[i] = (byte) decompressedString.charAt(i);
        }

        return decompressedBytes;
    }
}