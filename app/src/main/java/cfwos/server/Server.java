package cfwos.server;

import cfwos.model.database.Database;
import cfwos.Datagram;
import cfwos.model.WorkOrder;
import cfwos.util.Logger;

public class Server {
    private Database database;

    private Logger logger;

    public Server() {
        this.database = new Database();

        this.logger = new Logger("server_log.log");
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

    public void processDatagram(Datagram datagram) {
        Datagram received = datagram;
        received.getOperation();
        received.decompress();

        WorkOrder workOrder = WorkOrder.fromString(received.getCompressedData());
        System.out.println("WorkOrder received: " + workOrder);

        System.out.println("WorkOrder Code " + workOrder.getCode());
        System.out.println("WorkOrder Name " + workOrder.getName());
        System.out.println("WorkOrder Description " + workOrder.getDescription());
        System.out.println("WorkOrder Timestamp " + workOrder.getTimestamp());
    }

}
