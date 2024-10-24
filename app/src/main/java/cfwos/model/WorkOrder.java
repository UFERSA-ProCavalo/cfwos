package cfwos.model;

import java.io.Serializable; // Importando a interface Serializable
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkOrder implements Serializable { // Implementando Serializable
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private int code;
    private String name;
    private String description;
    private String timestamp;

    public WorkOrder(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.timestamp = LocalDateTime.now().format(formatter).toString();
    }

    public WorkOrder(int code, String name, String description, String timestamp) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        WorkOrder workOrder = (WorkOrder) obj;

        return code == workOrder.code;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(code);
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public static WorkOrder fromString(String str) {
        if (str == null || !str.startsWith("WorkOrder{") || !str.endsWith("}")) {
            throw new IllegalArgumentException("Invalid string format 1");
        }

        str = str.substring(10, str.length() - 1); // Remove "WorkOrder{" e "}"
        String[] parts = str.split(", ");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid string format 2");
        }

        int code = Integer.parseInt(parts[0].split("=")[1]);
        String name = parts[1].split("=")[1].replace("'", "");
        String description = parts[2].split("=")[1].replace("'", "");
        String timestamp = parts[3].split("=")[1].replace("'", "");

        return new WorkOrder(code, name, description, timestamp);
    }
}
