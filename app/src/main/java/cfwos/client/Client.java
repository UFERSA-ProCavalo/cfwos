package cfwos.client;

import java.util.Scanner;
import cfwos.model.entity.WorkOrder;
import cfwos.cache.CacheFIFO;
import cfwos.server.Server;

public class Client {
    private CacheFIFO<WorkOrder> cache;
    private Server server;
    private Scanner scanner;
    private String OperationMessage;
    private String name;

    public Client(Scanner scanner, Server server, String name) {
        this.cache = new CacheFIFO<>();
        this.server = server;
        this.scanner = scanner;
        this.name = name;
    }

    /*
     * INSERT METHOD
     */
    public void insertWorkOrder() {
        System.out.print("Enter WorkOrder ID: ");
        int code = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        /*
         * CHECKS IF THE WORKORDER IS IN THE CACHE
         * If it is, we return
         * If it isn't, we continue to check if it is in the database
         */
        if (isInCache(code, new WorkOrder(code, null, null))) {
            System.out.println("WorkOrder already exists in cache. \n"
                    + "WorkOder: " + cache.get(new WorkOrder(code, null, null)) + "\n\n"
                    + "Did you mean to update it?");
            Return();
        }

        if (isInDatabase(code)) {
            System.out.println("WorkOrder already exists in the database. \n"
                    + "WorkOder: " + server.getDatabase().searchWorkOrder(code) + "\n\n"
                    + "Did you mean to update it?");
            Return();
        }

        System.out.print("Enter WorkOrder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter WorkOrder description: ");
        String description = scanner.nextLine();

        WorkOrder workOrder = new WorkOrder(code, name, description);

        OperationMessage = " | WorkOrder inserted: " + workOrder;

        cache.add(workOrder); // add to cache
        server.getDatabase().addWorkOrder(code, name, description); // add to database

        System.out.println("\nWorkOrder inserted successfully.\n");

        LoggerUpdate(OperationMessage);
        Return();
    }

    /*
     * REMOVE METHOD
     */
    public void removeWorkOrder() {
        System.out.print("Enter WorkOrder ID to remove: ");
        int code = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        OperationMessage = " | WorkOrder removed: " + server.getDatabase().searchWorkOrder(code);

        /*
         * CHECKS IF THE WORKORDER IS IN THE CACHE
         * If it is, we remove it from the cache and the database, then return
         * If it isn't, we continue to check if it is in the database
         */
        if (isInCache(code, new WorkOrder(code, null, null))) {
            cache.remove(new WorkOrder(code, null, null)); // remove from cache
            server.getDatabase().removeWorkOrder(code); // remove from database

        } else if (server.getDatabase().searchWorkOrder(code) != null) {
            server.getDatabase().removeWorkOrder(code); // remove from database

        } else {
            System.out.println("WorkOrder not found in the database.\n");
            return;
        }

        System.out.println("\nWorkOrder removed successfully.\n");

        LoggerUpdate(OperationMessage);
        Return();
    }

    /*
     * UPDATE METHOD
     * Since the tree doesn't support updating,
     * we remove the old WorkOrder and insert a new one
     */
    public void updateWorkOrder() {
        System.out.print("Enter WorkOrder ID to update: ");
        int code = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        /*
         * CHECKS IF THE WORKORDER IS IN THE CACHE
         * If it is, we remove it from the cache, then proceed to update it
         * If it isn't, we continue to check if it is in the database
         */
        if (isInCache(code, new WorkOrder(code, null, null))) {
            cache.remove(new WorkOrder(code, null, null)); // remove from cache
        } else if (server.getDatabase().searchWorkOrder(code) == null) {
            System.out.println("WorkOrder not found in the database.\n");
            return;
        }

        OperationMessage = " | Update WorkOrder: " + server.getDatabase().searchWorkOrder(code);

        System.out.print("Enter new WorkOrder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new WorkOrder description: ");
        String description = scanner.nextLine();

        WorkOrder workOrder = new WorkOrder(code, name, description);

        OperationMessage += " -|- New WorkOrder: " + server.getDatabase().searchWorkOrder(code);
        server.getDatabase().updateWorkOrder(code, workOrder); // update in database
        cache.add(workOrder); // add to cache
        System.out.println("\nWorkOrder updated successfully.\n");

        LoggerUpdate(OperationMessage);
        Return();
    }

    /*
     * SEARCH METHOD
     */
    public void searchWorkOrder() {
        System.out.print("Enter WorkOrder ID to search: ");
        int code = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        WorkOrder workOrder = server.getDatabase().searchWorkOrder(code);
        if (workOrder != null) {
            String.format("WorkOrder found: %s", workOrder);
            System.out.println("WorkOrder found: " + workOrder);
        } else {
            System.out.println("WorkOrder not found.");
        }
        Return();
    }

    /*
     * LIST METHOD
     */
    public void listWorkOrders() {
        System.out.println("Listing existing WorkOrders:");
        server.getDatabase().showDatabase();

        Return();
    }

    public CacheFIFO<WorkOrder> getCache() {
        return cache;
    }

    /*
     * AUXILIARY METHODS
     */

    /*
     * Checks if the WorkOrder is in the cache
     */
    public boolean isInCache(int code, WorkOrder workOrder) {
        if (cache.get(workOrder) != null) {
            return true;
        }
        return false;
    }

    /*
     * Checks if the WorkOrder is in the database
     */
    public boolean isInDatabase(int code) {
        if (server.getDatabase().searchWorkOrder(code) != null) {
            return true;
        }
        return false;
    }

    /*
     * LOGGING METHOD
     */
    public void LoggerUpdate(String message) {
        server.log("Tree Height: " + server.getDatabase().getTreeHeight()
                + " | Tree Size: " + server.getDatabase().getSize()
                + " | Rotation in the AVL tree: " + (server.isUnbalanced() ? "Yes" : "No")
                + message);
    }

    /*
     * DEFAULT RETURN METHOD
     * Used to show the cache after every operation
     * then return to the main menu
     */
    public void Return() {
        if (cache.getSize() == 0) {
            System.out.println("\nCache is empty.\n");
        }
        System.out.println("\nListing Cache:");
        cache.showCache();
        System.out.println("\n");
        return;
    }
}
