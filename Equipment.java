package rentalSystem;

public class Equipment {
    private int serialNumber;
    private String description;
    private String type;
    private String model;
    private int year;
    private String dimensions;
    private int weight;
    private String location;
    private int quantity;
    private boolean status; // true if available, false if rented

    public Equipment(int serialNumber, String description, String type, String model,
                     int year, String dimensions, int weight, String location,
                     int quantity, boolean status) {
        this.serialNumber = serialNumber;
        this.description = description;
        this.type = type;
        this.model = model;
        this.year = year;
        this.dimensions = dimensions;
        this.weight = weight;
        this.location = location;
        this.quantity = quantity;
        this.status = status;
    }

    // Getter Methods

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getDimensions() {
        return dimensions;
    }

    public int getWeight() {
        return weight;
    }

    public String getLocation() {
        return location;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return status;
    }

    // Setter Methods

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nEquipment ID: " + serialNumber +
               "\nDescription: " + description +
               "\nType: " + type +
               "\nModel: " + model +
               "\nYear: " + year +
               "\nDimensions: " + dimensions +
               "\nWeight: " + weight + " lbs" +
               "\nLocation: " + location +
               "\nQuantity: " + quantity +
               "\nActive: " + (status ? "Yes" : "No");
    }
}
