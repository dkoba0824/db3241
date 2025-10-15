package rentalSystem;

public class Equipment {
    private int id;
    private String name;
    private String type;
    private boolean isAvailable;

    public Equipment(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isAvailable = true;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "Equipment ID: " + this.id + ", Name: " + this.name + ", Type: "
                + this.type + ", Available: " + (this.isAvailable ? "Yes" : "No");
    }
}
