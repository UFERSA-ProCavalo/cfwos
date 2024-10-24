package cfwos.model.database;

import cfwos.model.WorkOrder;

public class Database{
    private HashTableExternal<Integer, WorkOrder> database;

    public Database() {
        this.database = new HashTableExternal<>(100);
    }

    public void addWorkOrder(int code, String name, String description) {
        WorkOrder new_workOrder = new WorkOrder(code, name, description);
        database.insert(code, new_workOrder);
        
    }

    public void addWorkOrder(int code, String name, String description, String timestamp) {
        WorkOrder new_workOrder = new WorkOrder(code, name, description, timestamp);
        database.insert(code, new_workOrder);
    }

    public void addWorkOrder(WorkOrder workOrder) {
        database.insert(workOrder.getCode(), workOrder);
    }

    public void removeWorkOrder(int code) {
        database.remove(code);
    }

    public void updateWorkOrder(int code, WorkOrder workOrder) {
        WorkOrder temp = database.search(code);
        if (temp == null) {
            return;
        }
        temp = workOrder;
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

    public boolean isInDatabase(int code) {
        return database.contains(code);
    }
}
