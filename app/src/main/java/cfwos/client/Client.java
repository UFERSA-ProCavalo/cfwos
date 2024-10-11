package cfwos.client;

import java.util.InputMismatchException;
import java.util.Scanner;

import cfwos.model.entity.WorkOrder;
//import cfwos.cache.CacheFIFO;
import cfwos.cache.CacheRANDOM;
import cfwos.server.Server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Client {
    // private CacheFIFO<WorkOrder> cache;
    private CacheRANDOM<Integer, WorkOrder> cache;
    private Server server;
    private Scanner scanner;
    private String OperationMessage;
    public String name;

    public Client(Scanner scanner, Server server, String name) {
        // this.cache = new CacheFIFO<>();
        this.cache = new CacheRANDOM<>();
        this.server = server;
        this.scanner = scanner;
        this.name = name;
    }

    /*
     * INSERT METHOD
     */
    public void insertWorkOrder() {
        System.out.print("Enter WorkOrder ID: ");
        int code = ValidateCodeInput();
        scanner.nextLine(); // Consume the newline character

        /*
         * CHECKS IF THE WORKORDER IS IN THE CACHE
         * If it is, we return
         * If it isn't, we continue to check if it is in the database
         */
        if (isInCache(code)) {
            System.out.println("WorkOrder already exists in cache. \n"
                    + "WorkOder: " + cache.get(code) + "\n\n"
                    + "Did you mean to update it?");
            return;
        }

        if (isInDatabase(code)) {
            System.out.println("WorkOrder already exists in the database. \n"
                    + "WorkOder: " + server.getDatabase().searchWorkOrder(code) + "\n\n"
                    + "Did you mean to update it?");
            // cache.add(server.getDatabase().searchWorkOrder(code)); // add to cache
            return;
        }

        System.out.print("Enter WorkOrder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter WorkOrder description: ");
        String description = scanner.nextLine();

        String timestamp = ValidateDateInput();

        // System.out.println("Enter WorkOrder date (Blank for current time) - [default
        // format dd-MM-yyyy HH:mm:ss]: ");

        // String timestamp = scanner.nextLine();

        WorkOrder workOrder = new WorkOrder(code, name, description, timestamp);
        // cache.add(workOrder); // add to cache

        OperationMessage = " | WorkOrder inserted: " + workOrder;
        // int oldBalance = server.getDatabase().getBalanceCounter();
        // int newBalance = oldBalance;

        server.getDatabase().addWorkOrder(code, name, description, timestamp); // add to database
        String CollisionMessage = server.getDatabase().getCollisionMessage();

        // newBalance = server.getDatabase().getBalanceCounter();
        // boolean isUnbalanced = (oldBalance != newBalance);
        System.out.println("\nWorkOrder inserted successfully.\n");
        LoggerUpdate(OperationMessage, CollisionMessage);

        // return;
    }

    /*
     * REMOVE METHOD
     */
    public void removeWorkOrder() {
        System.out.print("Enter WorkOrder ID to remove: ");
        int code = ValidateCodeInput();
        scanner.nextLine(); // Consume the newline character

        OperationMessage = " | WorkOrder removed: " + server.getDatabase().searchWorkOrder(code);
        // int oldBalance = server.getDatabase().getBalanceCounter();
        // int newBalance = oldBalance;

        /*
         * CHECKS IF THE WORKORDER IS IN THE CACHE
         * If it is, we remove it from the cache and the database, then return
         * If it isn't, we continue to check if it is in the database
         */
        if (isInCache(code)) {
            cache.remove(code); // remove from cache
            server.getDatabase().removeWorkOrder(code); // remove from database

        } else if (isInDatabase(code)) {
            server.getDatabase().removeWorkOrder(code); // remove from database

        } else {
            System.out.println("WorkOrder not found in the database.\n");
            return;
        }

        // newBalance = server.getDatabase().getBalanceCounter();
        // boolean isUnbalanced = (oldBalance != newBalance);
        System.out.println("\nWorkOrder removed successfully.\n");

        server.getDatabase().getCollisionMessage();
        LoggerUpdate(OperationMessage);

        return;
    }

    /*
     * UPDATE METHOD
     * Since the tree doesn't support updating,
     * we remove the old WorkOrder and insert a new one
     */
    public void updateWorkOrder() {
        System.out.print("Enter WorkOrder ID to update: ");
        int code = ValidateCodeInput();
        scanner.nextLine(); // Consume the newline character

        /*
         * CHECKS IF THE WORKORDER IS IN THE CACHE
         * If it is, we remove it from the cache, then proceed to update it
         * If it isn't, we continue to check if it is in the database
         */
        if (isInCache(code)) {
            System.out.println("WorkOrder found in cache: " + cache.get(code));
        } else if (!isInDatabase(code)) {
            System.out.println("WorkOrder not found in the database.\n");

            return;
        }

        OperationMessage = "Update WorkOrder: " + server.getDatabase().searchWorkOrder(code);

        System.out.print("Enter new WorkOrder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new WorkOrder description: ");
        String description = scanner.nextLine();

        String timestamp = ValidateDateInput();

        WorkOrder workOrder = new WorkOrder(code, name, description, timestamp);

        server.getDatabase().updateWorkOrder(code, workOrder); // update in database
        cache.update(code, workOrder);; // update cache

        OperationMessage += " -|- New WorkOrder: " + server.getDatabase().searchWorkOrder(code);
        System.out.println("\nWorkOrder updated successfully.\n");

        LoggerUpdate(OperationMessage);
        return;
    }

    /*
     * SEARCH METHOD
     */
    public void searchWorkOrder() {
        System.out.print("Enter WorkOrder ID to search: ");
        int code = ValidateCodeInput();
        scanner.nextLine(); // Consume the newline character

        if (isInCache(code)) {
            String.format("WorkOrder found in cache: %s", cache.get(code));
            //System.out.println("WorkOrder found in cache: " + cache.get(code));

            return;
        }

        if (isInDatabase(code)) {
            String.format("WorkOrder found in the database: %s", server.getDatabase().searchWorkOrder(code));
            System.out.println("WorkOrder found in the database: " + server.getDatabase().searchWorkOrder(code));
            cache.add(code,server.getDatabase().searchWorkOrder(code)); // add to cache

            return;
        }

        System.out.println("WorkOrder not found.");

        return;

    }

    /*
     * LIST METHOD
     */
    public void listWorkOrders() {
        System.out.println("Listing existing WorkOrders:");
        server.getDatabase().showDatabase();

        return;
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

    /*
     * Checks if the WorkOrder is in the cache
     */
    private boolean isInCache(int code) {
        if (cache.get(code) != null) {
            return true;
        }
        return false;
    }

    /*
     * Checks if the WorkOrder is in the database
     */
    private boolean isInDatabase(int code) {
        if (server.getDatabase().searchWorkOrder(code) != null) {
            return true;
        }
        return false;
    }

    /*
     * LOGGING METHOD
     */
    public void LoggerUpdate(String message, String CollisionMessage) {
        String logMessage = "Cache Size: " + cache.getSize()
                + " | Database Size: " + server.getDatabase().getSize()
                + " | Collision in the database: "
                + (CollisionMessage != null ? "Yes, " + CollisionMessage : "No collision")
                + " | " + message;

        // Log the constructed message using the logger
        server.log("INFO", logMessage);
    }

    public void LoggerUpdate(String message) {
        String logMessage = "Cache Size: " + cache.getSize()
                + " | Database Size: " + server.getDatabase().getSize()
                + " | " + message;

        // Log the constructed message using the logger
        server.log("INFO", logMessage);
    }

    // Ensure to close the logger when done
    public void closeLogger() {
        server.closeLogger();
    }

    /*
     * DEFAULT RETURN METHOD
     * Used to show the cache after every operation
     * then return to the main menu
     */
    public void showCache() {
        if (cache.getSize() == 0) {
            System.out.println("\nCache is empty.\n");
            return;
        }
        System.out.println("\nListing Cache:\n");
        cache.showCache();
        System.out.println("\n");
        return;
    }

    /*
     * SEARCH 20 ENTRIES
     * Made to fill for the cache and test its policy (RANDOM) and colision
     * treatment
     */
    //do 20 random searches of existing WorkOrders in the database and add them to the cache
    public void search20Entries() {
        for (int i = 0; i < 20;) {
            int code = (int) (Math.random() * 100);
            if (isInCache(code)) {
                continue;
            }
            if (isInDatabase(code)) {
                cache.add(code, server.getDatabase().searchWorkOrder(code));
                i++;
            }
        }
    }

    /*
     * VALIDATE DATE INPUT
     * Ensures the date is in the correct format
     */
    private String ValidateDateInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timestamp;

        while (true) {
            System.out
                    .println("Enter WorkOrder date (Blank for current time) - [default format dd-MM-yyyy HH:mm:ss]: ");
            timestamp = scanner.nextLine();

            if (timestamp.isEmpty()) {
                timestamp = LocalDateTime.now().format(formatter).toString(); // Use the current date and time if input
                                                                              // is blank
                break;
            } else {
                try {
                    timestamp = LocalDateTime.parse(timestamp, formatter).toString();
                    break; // Break the loop if parsing is successful
                } catch (DateTimeParseException e) {
                    System.out.println(
                            "Invalid date format. Please enter the date in the format dd-MM-yyyy HH:mm:ss! Or leave it blank for the current time.");
                }
            }
        }
        return timestamp;
    }

    /*
     * VALIDATE CODE INPUT
     * Ensures the code is an natural number
     */
    private int ValidateCodeInput() {
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

}
