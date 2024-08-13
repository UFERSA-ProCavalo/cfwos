package cfwos.server;

import cfwos.util.Logger;

public class Server {
    private Database database;
    
    private Logger logger;
    public int BeforeBalanceCounter;
    public int AfterBalanceCounter;

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

}
