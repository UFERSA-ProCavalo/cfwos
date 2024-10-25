package cfwos.server;

import cfwos.model.database.Database;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cfwos.model.Datagram;
import cfwos.model.KMP;
import cfwos.model.WorkOrder;
import cfwos.model.compression.HuffmanTree;
import cfwos.util.Logger;

public class Server {
    private Database database;
    private Logger logger;
    public Datagram response;
    private String message;

    public Server() {
        this.database = new Database();
        this.logger = new Logger("server_log.log");
        this.response = null;
        this.message = "";
    }

    public Database getDatabase() {
        return database;
    }

    public void log(String message) {
        logger.log(message);
    }

    public void log(String level, String message) {
        logger.log(level, message);
    }

    public void closeLogger() {
        logger.close();
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Datagram setResponse(Datagram message) {
        response = message;
        return response;
    }

    public Datagram getResponse() {
        return response;
    }

    public void processDatagram(Datagram datagram, HuffmanTree huffmanTree) {
        Datagram received = datagram;
        if (received == null) {
            return;
        }

        received.decompress(huffmanTree);

        WorkOrder workOrder = null;
        if (!received.getCompressedData().equals(received.getOperation())) {
            workOrder = WorkOrder.fromString(received.getCompressedData());
        }

        switch (received.getOperation()) {
            case "INSERT":
                // check if the work order was added to the database
                database.addWorkOrder(workOrder);
                if (database.searchWorkOrder(workOrder.getCode()) == null) {
                    System.out.println("ERROR! WorkOrder not added to the database");
                    return;
                }

                message = "WorkOrder added successfully. \n WorkOrder: " + workOrder.toString();
                response = new Datagram("ADD", message);
                response.compress();
                setResponse(response);
                break;

            case "REMOVE":
                if (workOrder == null) {
                    message = "ERROR! WorkOrder not found!";
                    response = new Datagram("ERROR", message);
                } else {
                    database.removeWorkOrder(workOrder.getCode());
                    message = "WorkOrder removed successfully. \n WorkOrder: " + workOrder.toString();
                }

                response = new Datagram("REMOVE", message);
                response.compress();
                setResponse(response);
                break;

            case "UPDATE":
                if (workOrder == null) {
                    message = "ERROR! WorkOrder not found!";
                    response = new Datagram("ERROR", message);
                } else {
                    database.updateWorkOrder(workOrder.getCode(), workOrder);
                    message = "WorkOrder updated successfully.\n WorkOrder: " + workOrder.toString();
                }
                response = new Datagram("UPDATE", message);
                response.compress();
                setResponse(response);
                break;

            case "SEARCH":
                if (workOrder == null) {
                    message = "ERROR! WorkOrder not found!";
                    response = new Datagram("ERROR", message);
                } else {
                    database.searchWorkOrder(workOrder.getCode());
                    message = "WorkOrder found: " + workOrder.toString();
                    response = new Datagram("SEARCH", message);
                }
                response.compress();
                setResponse(response);
                break;

            case "LIST":
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                PrintStream originalOut = System.out;
                System.setOut(ps);
                database.showDatabase();
                System.setOut(originalOut);

                message = baos.toString();
                response = new Datagram("LIST", message);
                response.compress();
                setResponse(response);
                break;

            case "COUNT":
                database.getSize();
                message = "Database size: " + database.getSize();
                response = new Datagram("COUNT", message);
                response.compress();
                setResponse(response);
                break;

            default:
                response = new Datagram("ERROR", "Unknown operation");
                response.compress();
                setResponse(response);
                break;
        }

        // WorkOrder workOrder = WorkOrder.fromString(received.getCompressedData());
        // System.out.println("WorkOrder received: " + workOrder);

        // System.out.println("WorkOrder Code " + workOrder.getCode());
        // System.out.println("WorkOrder Name " + workOrder.getName());
        // System.out.println("WorkOrder Description " + workOrder.getDescription());
        // System.out.println("WorkOrder Timestamp " + workOrder.getTimestamp());
    }

    public static List<String> searchInLog(String filename, String keyword) {
        KMP kmp = new KMP();
        StringBuilder logContent = new StringBuilder();
        List<String> matchingLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                logContent.append(line + "\n");

                if (kmp.buscar(keyword, line) > 0) {
                    matchingLines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the log file: " + e.getMessage());
            return matchingLines;
        }

        return matchingLines;
    }

}
