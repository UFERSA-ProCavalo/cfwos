package cfwos;

import cfwos.server.Server;
import cfwos.client.Client;
import java.util.List;
import java.util.Scanner;

public class App {

        private static Scanner scanner = null;
        private static Server server = null;
        private static Client client = null;

        public static void main(String[] args) throws Exception {
                System.out.println("\033[2J\033[1;1H");
                scanner = new Scanner(System.in);
                server = new Server();
                client = new Client(scanner, server, "Client Joaquim Barbosa");

                // Add 60 work orders to the server
                App.insert60Entries(server, client);
                // server.getDatabase().showDatabase();
                System.out.println("Welcome to the Cache Flow Work Order System!");
                System.out.println("Server initialized.");

                // Initialize the client

                System.out.println("\nClient initialized. Ready to interact with the server.");

                // Interact with the server through the client

                boolean running = true;
                while (running) {

                        client.showCache();

                        System.out.println("\n");
                        System.out.println("Client Menu:");
                        System.out.println("[1]. Insert WorkOrder");
                        System.out.println("[2]. Remove WorkOrder");
                        System.out.println("[3]. Update WorkOrder");
                        System.out.println("[4]. Search WorkOrder");
                        System.out.println("[5]. Show all WorkOrders");
                        System.out.println("[6]. List WorkOrder count");
                        System.out.println("[7]. Search operations in the log");
                        System.out.println("[8]. Exit");
                        System.out.print("Choose an option: ");

                        char choice = scanner.next().charAt(0);
                        System.out.println("\033[2J\033[1;1H");

                        switch (choice) {
                                case '1':
                                        client.insertWorkOrder();
                                        break;
                                case '2':
                                        client.removeWorkOrder();
                                        break;
                                case '3':
                                        client.updateWorkOrder();
                                        break;
                                case '4':
                                        client.searchWorkOrder();
                                        break;
                                case '5':
                                        client.listWorkOrders();
                                        break;
                                case '6':
                                        client.listWorkOrderCount();
                                        break;
                                case '7':
                                        logSubMenu(scanner);
                                        break;
                                case '8':
                                        running = false;
                                        break;
                                case '0':
                                        System.out.println("Adding 20 entries to the cach!");
                                        client.search20Entries();
                                        break;
                                default:
                                        System.out.println("Operation not listed, please try again.");
                                        break;
                        }
                }

                System.out.println("Program terminated.");
                scanner.close();
        }

        public static void logSubMenu(Scanner scanner) {
                System.out.println("Log Menu:");
                System.out.println("[1]. Inserted");
                System.out.println("[2]. Removed");
                System.out.println("[3]. Updated");
                System.out.println("[4]. Searched");
                System.out.print("Choose an option: ");

                char choice = scanner.next().charAt(0);
                System.out.println("\033[2J\033[1;1H");

                String filename = "server_log.log";

                List<String> foundLines;

                switch (choice) {
                        case '1':
                                foundLines = Server.searchInLog(filename, "inserted");
                                break;
                        case '2':
                                foundLines = Server.searchInLog(filename, "Removed");
                                break;
                        case '3':
                                foundLines = Server.searchInLog(filename, "Updated");
                                break;
                        case '4':
                                foundLines = Server.searchInLog(filename, "Searched");
                                break;
                        default:
                                System.out.println("Operation not listed, please try again.");
                                return;

                }

                // Display the found lines
                if (foundLines.isEmpty()) {
                        System.out.println("No lines found with the specified pattern.");
                } else {
                        System.out.println("Found lines:");
                        for (String line : foundLines) {
                                System.out.println(line);
                        }
                }
        }

        public static void insert60Entries(Server server, Client client) {
                for (int i = 1; i < 100; i++) {
                        server.getDatabase().addWorkOrder(i, "WorkOrder" + i, "Description" + i);
                        String message = " | WorkOrder inserted: " + server.getDatabase().searchWorkOrder(i);
                        String CollisionMessage = server.getDatabase().getCollisionMessage();
                        client.LoggerUpdate(message, CollisionMessage);
                }
        }

}