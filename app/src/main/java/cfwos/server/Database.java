package cfwos.server;

import cfwos.model.entity.WorkOrder;
//import cfwos.model.tree.TreeAVL;
import cfwos.model.hash.HashTableExternal;

public class Database{
    private HashTableExternal<Integer, WorkOrder> database;

    public Database() {
        this.database = new HashTableExternal<>(100);
    }

    public void addWorkOrder(int code, String name, String description) {
        WorkOrder workOrder = new WorkOrder(code, name, description);
        database.insert(code, workOrder);
        
    }

    public void addWorkOrder(int code, String name, String description, String timestamp) {
        WorkOrder workOrder = new WorkOrder(code, name, description, timestamp);
        database.insert(code, workOrder);
    }

    public void removeWorkOrder(int code) {
        database.remove(code);
    }

    public void updateWorkOrder(int code, String name, String description, String timestamp) {
        WorkOrder temp = database.search(code);
        if (temp == null) {
            return;
        }
        temp.setName(name);
        temp.setDescription(description);
        temp.setTimestamp(timestamp);
        //database.Insert(code, workOrder);
    }

    public WorkOrder searchWorkOrder(int code) {
        return database.search(code);
    }

    public void showDatabase() {
        database.show();
    }

    // public void showDatabaseReverse() {
    //     database.ShowReverse();
    // }

    // public int getTreeHeight() {
    //     return database.getTreeHeight();
    // }

    public int getSize() {
        return database.getSize();
    }

    // public int getBalanceCounter() {
    //     return database.getBalanceCounter();
    // }

    // public List<Collision<Integer>> getCollisions() {
    //     return database.getCollisions();
    // }
    public String getCollisionMessage() {
        return database.getCollision();
    }
}
