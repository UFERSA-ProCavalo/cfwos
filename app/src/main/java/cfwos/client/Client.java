package cfwos.client;

import java.util.InputMismatchException;
import java.util.Scanner;

import cfwos.model.Datagram;
import cfwos.model.WorkOrder;
import cfwos.model.cache.CacheLRU;
import cfwos.server.Server;
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
        if (server.getDatabase().isInDatabase(code)) {
            handleExistingWorkOrderInDatabase(code);
            return;
        }

        WorkOrder workOrder = createNewWorkOrder(code);

        sendDatagram("INSERT", workOrder);
        receiveDatagram(server.response);

        logOperation("Inserted WorkOrder: " + workOrder);
    }

    /*
     * REMOVE METHOD
     */
    public void removeWorkOrder() {
        System.out.print("Enter WorkOrder ID to remove: ");
        int code = validateCodeInput();
        scanner.nextLine();

        if (cache.isInCache(code)) {
            System.out.println("WorkOrder found in cache: " + cache.get(code));
            cache.remove(code);
        } else if (!server.getDatabase().isInDatabase(code)) {
            System.out.println("WorkOrder not found in the database.\n");
            return;
        }

        WorkOrder removedWorkOrder = server.getDatabase().searchWorkOrder(code);
        sendDatagram("REMOVE", removedWorkOrder);
        receiveDatagram(server.response);

        System.out.println("\nWorkOrder removed successfully.\n");
        logOperation("Removed WorkOrder: " + removedWorkOrder);
    }

    /*
     * UPDATE METHOD
     */
    public void updateWorkOrder() {
        System.out.print("Enter WorkOrder ID to update: ");
        int code = validateCodeInput();
        scanner.nextLine();

        if (cache.isInCache(code)) {
            System.out.println("WorkOrder found in cache: " + cache.get(code));
        } else if (!server.getDatabase().isInDatabase(code)) {
            System.out.println("WorkOrder not found in the database.\n");
            return;
        }

        WorkOrder oldWorkOrder = server.getDatabase().searchWorkOrder(code);
        WorkOrder updatedWorkOrder = createNewWorkOrder(code);

        sendDatagram("UPDATE", updatedWorkOrder);
        receiveDatagram(server.response);

        cache.update(code, updatedWorkOrder);
        System.out.println("\nWorkOrder updated successfully.\n");
        logOperation("Updated WorkOrder: " + oldWorkOrder + " | to: " + updatedWorkOrder);
    }

    /*
     * SEARCH METHOD
     */
    public void searchWorkOrder() {
        System.out.print("Enter WorkOrder ID to search: ");
        int code = validateCodeInput();
        scanner.nextLine();

        if (cache.isInCache(code)) {
            System.out.println("WorkOrder found in cache: " + cache.get(code));
            return;
        }

        WorkOrder searchedWorkOrder = server.getDatabase().searchWorkOrder(code);
        sendDatagram("SEARCH", searchedWorkOrder);
        receiveDatagram(server.response);

        if (searchedWorkOrder != null) {
            cache.add(code, searchedWorkOrder);
            System.out.println("WorkOrder found: " + searchedWorkOrder);
            logOperation("Searched WorkOrder: " + searchedWorkOrder);
            return;

        }

        System.out.println("WorkOrder not found.");
    }

    /*
     * LIST METHOD
     */
    public void listWorkOrders() {
        System.out.println("Listing existing WorkOrders:");
        sendDatagram("LIST", null);
        receiveDatagram(server.response);
    }

    /*
     * LIST WORKORDER COUNT
     */
    public void listWorkOrderCount() {
        System.out.println("WorkOrder count: " + server.getDatabase().getSize());
        sendDatagram("COUNT", null);
        receiveDatagram(server.response);
    }

    /*
     * AUXILIARY METHODS
     */
    /*
     * Check if the WorkOrder is in the database
     */
    public void sendDatagram(String operation, WorkOrder workOrder) {
        String Uncompressed;
        String Compressed;
        Datagram datagram = null;

        if (workOrder == null) {
            datagram = new Datagram(operation, operation);
        } else {
            datagram = new Datagram(operation, workOrder.toString());
        }

        Uncompressed = datagram.getCompressedData();
        datagram.compress();

        Compressed = datagram.getCompressedData();

        server.processDatagram(datagram, datagram.getHuffmanTree());

        // Mensagens organizadas para facilitar a visualização
        System.out.println("================REQUEST================\n");
        System.out.println("Uncompressed request:\n" + Uncompressed + "\n");
        System.out.println("Compressed request:\n" + Compressed + "\n");
    }

    public void receiveDatagram(Datagram datagram) {
        String Uncompressed;
        String Compressed;

        Datagram received = datagram;
        Compressed = received.getCompressedData();

        received.decompress(received.getHuffmanTree());

        Uncompressed = received.getCompressedData();

        System.out.println("================RESPONSE================\n");
        System.out.println("Compressed response:\n" + Compressed + "\n");
        System.out.println("Uncompressed response:\n" + Uncompressed + "\n");
        System.out.println("========================================\n");
    }

    /*
     * LOGGING METHOD
     */
    /*
     * LOGGING METHOD
     */
    public void LoggerUpdate(String message, String collisionMessage) {
        String logMessage = "Cache Size: " + cache.getSize()
                + " | Database Size: " + server.getDatabase().getSize()
                + " | " + message;

        if (collisionMessage != null) {
            logMessage += " | Collision in the database: Yes, " + collisionMessage;
        } else {
            logMessage += " | Collision in the database: No collision";
        }

        server.log("INFO", logMessage);
    }

    private void logOperation(String message) {
        String logMessage = "Cache Size: " + cache.getSize() +
                " | Database Size: " + server.getDatabase().getSize() +
                " | " + message;
        server.log("INFO", logMessage);
    }

    public void LoggerUpdate(String message) {
        String logMessage = "Cache Size: " + cache.getSize()
                + " | Database Size: " + server.getDatabase().getSize()
                + " | " + message;

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
        System.out.println(cache.getSize());
        cache.showCache();
    }

    public void search20Entries() {
        for (int i = 0; i < 20;) {
            int code = (int) (Math.random() * 100);
            if (!cache.isInCache(code) && server.getDatabase().isInDatabase(code)) {
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
    }

    private void handleExistingWorkOrderInDatabase(int code) {
        System.out.println("WorkOrder already exists in the database: " + server.getDatabase().searchWorkOrder(code));
    }
}
