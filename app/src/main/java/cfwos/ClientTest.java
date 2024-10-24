package cfwos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import cfwos.model.WorkOrder;
import cfwos.server.Server;
import cfwos.util.Util;

public class ClientTest {

    private static Server server = new Server();

    private static Scanner scanner = new Scanner(System.in);

    private static WorkOrder createNewWorkOrder(int code) {
        System.out.print("Enter WorkOrder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter WorkOrder description: ");
        String description = scanner.nextLine();

        String timestamp = validateDateInput();

        return new WorkOrder(code, name, description, timestamp);
    }

    private static String validateDateInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timestamp;

        while (true) {
            System.out
                    .println("Enter WorkOrder date (Blank for current time) - [default format dd-MM-yyyy HH:mm:ss]: ");
            timestamp = scanner.nextLine();

            if (timestamp.isEmpty()) {
                timestamp = LocalDateTime.now().format(formatter);
                break;
            } else {
                try {
                    timestamp = LocalDateTime.parse(timestamp, formatter).toString();
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println(
                            "Invalid date format. Please enter the date in the format dd-MM-yyyy HH:mm:ss! Or leave it blank for the current time.");
                }
            }
        }
        return timestamp;
    }

    public static void main(String[] args) {
        String message = "aaaaaaabbbbbbbbffffffff";
        String pattern = "mensagem";
        Util.FrequencyTable frequencyTable = new Util.FrequencyTable(message.length());

        // Calcular a frequência dos caracteres
        for (char c : message.toCharArray()) {
            frequencyTable.add(c);
        }

        int n = frequencyTable.getSize();
        char[] characters = frequencyTable.getCharacters();
        int[] frequencies = frequencyTable.getFrequencies();

        // print frequency table
        System.out.println("Frequency Table:");
        for (int i = 0; i < n; i++) {
            System.out.println(characters[i] + ": " + frequencies[i]);
        }

        // Criar a árvore de Huffman
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.createTree(n, characters, frequencies);

        // Verificar se o padrão está presente na mensagem
        KMP kmp = new KMP();
        kmp.buscar(pattern, message);

        // Gerar os códigos de Huffman
        String[] huffmanCodes = huffmanTree.generateHuffmanCodes();

        // Imprimir os códigos de Huffman para conferir
        huffmanTree.printCodes();

        // Comprimir a mensagem
        String compressedMessage = huffmanTree.compress(message, huffmanCodes);
        System.out.println("Mensagem comprimida: " + compressedMessage);

        // Descomprimir a mensagem
        String decompressedMessage = huffmanTree.decompress(compressedMessage);
        System.out.println("Mensagem descomprimida: " + decompressedMessage);

        // Serializar e deserializar um objeto WorkOrder
        int code = 123;

        WorkOrder workOrder = createNewWorkOrder(code);

        Datagram datagram = new Datagram("inserir", workOrder.toString(), huffmanCodes);
        System.out.println("Datagrama: " + datagram);
        datagram.compress();
        server.processDatagram(datagram);


    }
}