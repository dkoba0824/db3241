package rentalSystem;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private String type;
    private int phone;
    private String email;
    private String joinDate;
    private String address;
    private int warehouseDistance;

    public Member(int id, String firstName, String lastName, String type,
                  int phone, String email, String joinDate,
                  String address, int warehouseDistance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.joinDate = joinDate;
        this.address = address;
        this.warehouseDistance = warehouseDistance;
    }

    // Getter Methods

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getType() {
        return type;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getAddress() {
        return address;
    }

    public int getWarehouseDistance() {
        return warehouseDistance;
    }

    // Setter Methods

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWarehouseDistance(int warehouseDistance) {
        this.warehouseDistance = warehouseDistance;
    }

    @Override
    public String toString() {
        return "\nMember ID: " + id +
               "\nName: " + firstName + " " + lastName +
               "\nType: " + type +
               "\nPhone: " + phone +
               "\nEmail: " + email +
               "\nJoin Date: " + joinDate +
               "\nAddress: " + address +
               "\nDistance from Warehouse: " + warehouseDistance + " miles";
    }
}
