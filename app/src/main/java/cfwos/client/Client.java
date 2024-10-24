package cfwos.client;

import java.util.InputMismatchException;
import java.util.Scanner;

import cfwos.Datagram;
import cfwos.HuffmanTree;
import cfwos.model.WorkOrder;
import cfwos.model.cache.CacheLRU;
import cfwos.server.Server;
import cfwos.util.Util;
import cfwos.util.Util.FrequencyTable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Client {
    private CacheLRU<Integer, WorkOrder> cache;
    private Server server;
    private Scanner scanner;
    public String name;

    public Client(Scanner scanner, Server server, String name) {
        this.cache = new CacheLRU<>();
        this.server = server;
        this.scanner = scanner;
        this.name = "Isaque Júnior";
    }

    /*
     * INSERT METHOD
     */
    public void insertWorkOrder() {
        System.out.print("Enter WorkOrder ID: ");
        int code = validateCodeInput();
        scanner.nextLine(); // Consume the newline character

        if (cache.isInCache(code)) {
            handleExistingWorkOrderInCache(code);
            return;
        }

        if (isInDatabase(code)) {
            handleExistingWorkOrderInDatabase(code);
            return;
        }

        WorkOrder workOrder = createNewWorkOrder(code);
        // Datagram datagram = new Datagram("insert", workOrder);
        
        // datagram.compress();
        // System.out.println("Compressed data: " + datagram.getCompressedData());
        // System.out.println("Huffman codes: " + datagram.getHuffmanCodes());
        
        // datagram.decompress();

        // WorkOrder workOrderDecompressed = (WorkOrder) datagram.deserializeObject();
        // System.out.println("Decompressed data: " + workOrderDecompressed);
        // server.processDatagram(datagram);

        cache.add(code, workOrder);
        server.getDatabase().addWorkOrder(workOrder);
        System.out.println("\nWorkOrder inserted successfully.\n");
        logOperation("Inserted WorkOrder: " + workOrder);
    }

    /*
     * REMOVE METHOD
     */
    public void removeWorkOrder() {
        System.out.print("Enter WorkOrder ID to remove: ");
        int code = validateCodeInput();
        scanner.nextLine(); // Consume the newline character

        if (cache.isInCache(code)) {
            cache.remove(code);
            server.getDatabase().removeWorkOrder(code);
        } else if (isInDatabase(code)) {
            server.getDatabase().removeWorkOrder(code);
        } else {
            System.out.println("WorkOrder not found in the database.\n");
            return;
        }

        System.out.println("\nWorkOrder removed successfully.\n");
        logOperation("Removed WorkOrder with ID: " + code);
    }

    /*
     * UPDATE METHOD
     */
    public void updateWorkOrder() {
        System.out.print("Enter WorkOrder ID to update: ");
        int code = validateCodeInput();
        scanner.nextLine(); // Consume the newline character

        if (cache.isInCache(code)) {
            System.out.println("WorkOrder found in cache: " + cache.get(code));
        } else if (!isInDatabase(code)) {
            System.out.println("WorkOrder not found in the database.\n");
            return;
        }

        WorkOrder updatedWorkOrder = createNewWorkOrder(code);
        server.getDatabase().updateWorkOrder(code, updatedWorkOrder);
        cache.update(code, updatedWorkOrder);
        System.out.println("\nWorkOrder updated successfully.\n");
        logOperation("Updated WorkOrder with ID: " + code);
    }

    /*
     * SEARCH METHOD
     */
    public void searchWorkOrder() {
        System.out.print("Enter WorkOrder ID to search: ");
        int code = validateCodeInput();
        scanner.nextLine(); // Consume the newline character

        if (cache.isInCache(code)) {
            System.out.println("WorkOrder found in cache: " + cache.get(code));
            return;
        }

        if (isInDatabase(code)) {
            System.out.println("WorkOrder found in the database: " + server.getDatabase().searchWorkOrder(code));
            cache.add(code, server.getDatabase().searchWorkOrder(code));
            return;
        }

        System.out.println("WorkOrder not found.");
    }

    /*
     * LIST METHOD
     */
    public void listWorkOrders() {
        System.out.println("Listing existing WorkOrders:");
        server.getDatabase().showDatabase();
    }

    /*
     * LIST WORKORDER COUNT
     */
    public void listWorkOrderCount() {
        System.out.println("WorkOrder count: " + server.getDatabase().getSize());
    }

    /*
     * AUXILIARY METHODS
     */
    private boolean isInDatabase(int code) {
        return server.getDatabase().searchWorkOrder(code) != null;
    }

    /*
     * LOGGING METHOD
     */
    private void logOperation(String message) {
        String logMessage = "Cache Size: " + cache.getSize() +
                " | Database Size: " + server.getDatabase().getSize() +
                " | " + message;
        server.log("INFO", logMessage);
    }

    public void closeLogger() {
        server.closeLogger();
    }

    public void showCache() {
        if (cache.getSize() == 0) {
            System.out.println("\nCache is empty.\n");
            return;
        }
        System.out.println("\nListing Cache:\n");
        cache.showCache();
    }

    public void search20Entries() {
        for (int i = 0; i < 20;) {
            int code = (int) (Math.random() * 100);
            if (!cache.isInCache(code) && isInDatabase(code)) {
                cache.add(code, server.getDatabase().searchWorkOrder(code));
                i++;
            }
        }
    }

    private String validateDateInput() {
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

    private int validateCodeInput() {
        int code;
        while (true) {
            try {
                code = scanner.nextInt();
                if (code < 0) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid code. Please enter a valid positive number.");
                scanner.nextLine();
            }
        }
        return code;
    }

    private WorkOrder createNewWorkOrder(int code) {
        System.out.print("Enter WorkOrder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter WorkOrder description: ");
        String description = scanner.nextLine();

        String timestamp = validateDateInput();

        return new WorkOrder(code, name, description, timestamp);
    }

    private void handleExistingWorkOrderInCache(int code) {
        System.out.println("WorkOrder already exists in cache: " + cache.get(code));
        // Optionally prompt for update
    }

    private void handleExistingWorkOrderInDatabase(int code) {
        System.out.println("WorkOrder already exists in the database: " + server.getDatabase().searchWorkOrder(code));
        // Optionally prompt for update
    }
}
