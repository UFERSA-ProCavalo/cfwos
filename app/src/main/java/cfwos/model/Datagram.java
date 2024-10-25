package cfwos.model;

import cfwos.model.compression.HuffmanTree;
import cfwos.util.Util;

public class Datagram {

    private String operation;
    private String compressedData;
    private String[] huffmanCodes;
    private HuffmanTree huffmanTree;

    public Datagram(String operation, String compressedData) {
        this.operation = operation;
        this.compressedData = compressedData;
        this.huffmanCodes = null;
        this.huffmanTree = new HuffmanTree();
    }

    public Datagram(String operation){
        this.operation = operation;
        this.compressedData = "";
        this.huffmanCodes = null;
        this.huffmanTree = new HuffmanTree();
    }

    public String getOperation() {
        return operation;
    }

    public String getCompressedData() {
        return compressedData;
    }

    public String[] getHuffmanCodes() {
        return huffmanCodes;
    }

    public HuffmanTree getHuffmanTree() {
        return huffmanTree;
    }

    public Datagram compress() {
        Util.FrequencyTable frequencyTable = new Util.FrequencyTable(compressedData.length());

        // Calcular a frequência dos caracteres
        for (char c : compressedData.toCharArray()) {
            frequencyTable.add(c);
        }

        int n = frequencyTable.getSize();
        char[] characters = frequencyTable.getCharacters();
        int[] frequencies = frequencyTable.getFrequencies();

        huffmanTree.createTree(n, characters, frequencies);
        huffmanCodes = huffmanTree.generateHuffmanCodes();

        compressedData = huffmanTree.compress(compressedData, huffmanCodes);
        //System.out.println("Compressed data: " + compressedData);
        return this;
    }

    public Datagram decompress(HuffmanTree huffmanTree) {
        String decompressedData = huffmanTree.decompress(compressedData);
        //System.out.println("Decompressed data: " + decompressedData);
        compressedData = decompressedData;
        return this;
    }

}
