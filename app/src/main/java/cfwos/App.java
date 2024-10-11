package cfwos;

import cfwos.server.Server;
import cfwos.client.Client;

import java.util.Scanner;

public class App {

        public static void main(String[] args) throws Exception {
                System.out.println("\033[2J\033[1;1H");
                Scanner scanner = new Scanner(System.in);
                Server server = new Server();
                Client client = new Client(scanner, server, "Client Joaquim Barbosa");

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
                        System.out.println("[7]. Exit");
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

        public static void insert60Entries(Server server, Client client) {
                for (int i = 0; i < 74; i++) {
                        server.getDatabase().addWorkOrder(i, "WorkOrder" + i, "Description" + i);
                        String message = " | WorkOrder inserted: " + server.getDatabase().searchWorkOrder(i);
                        String CollisionMessage = server.getDatabase().getCollisionMessage();
                        client.LoggerUpdate(message, CollisionMessage);
                }
        }
}