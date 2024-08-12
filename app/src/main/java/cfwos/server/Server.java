package cfwos.server;

import cfwos.util.Logger;

public class Server {
    private Database database;
    
    private Logger logger;
    public int BeforeBalanceCounter;
    public int AfterBalanceCounter;

    public Server() {
        this.database = new Database();
        
        this.logger = new Logger("server_log.txt");
    }

    public Database getDatabase() {
        return database;
    }

    public void log(String message) {
        logger.log(message);
    }


    public Logger getLogger() {
        return logger;
    }

    public boolean isUnbalanced() {
        BeforeBalanceCounter = database.getBalanceCounter();
        AfterBalanceCounter = database.getBalanceCounter();
        return BeforeBalanceCounter != AfterBalanceCounter;
    }

}
