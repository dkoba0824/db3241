package rentalSystem;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Equipment> equipmentList = new ArrayList<>();
    private static ArrayList<Member> memberList = new ArrayList<>();

// Main Menu
    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n--- Company Management System ---\n");
            System.out.println("1. Equipment");
            System.out.println("2. Drone Fleet");
            System.out.println("3. Members");
            System.out.println("4. Rental and Delivery Options");
            System.out.println("5. Reports");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1 -> equipmentMenu();
                case 2 -> droneMenu();
                case 3 -> memberMenu();
                case 4 -> rentalMenu();
                case 5 -> reportsMenu();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }

// Equipment Menu
    private static void equipmentMenu() {
        int choice;
        do {
            System.out.println("\n--- Equipment Menu ---\n");
            System.out.println("1. Add Equipment");
            System.out.println("2. List Equipment");
            System.out.println("3. Update Equipment");
            System.out.println("4. Remove Equipment");
            System.out.println("5. Search Equipment");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1 -> addEquipment();
                case 2 -> listEquipment();
                case 3 -> updateEquipment();
                case 4 -> removeEquipment();
                case 5 -> searchEquipment();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addEquipment() {
        System.out.println("Adding new equipment...");
        System.out.print("Enter equipment serial number: ");
        int serialNumber = getIntInput();

        //Check for duplicate serial number
        if (findEquipmentBySerial(serialNumber) != null) {
            System.out.println("Equipment with this serial number already exists!");
            return;
        }

        System.out.print("Enter equipment description: ");
        String description = scanner.nextLine();
        System.out.print("Enter equipment type: ");
        String type = scanner.nextLine();
        System.out.print("Enter equipment model: ");
        String model = scanner.nextLine();
        System.out.print("Enter equipment year: ");
        int year = getIntInput();
        System.out.print("Enter equipment dimensions (ex: 10x20x30 in): ");
        String dimensions = scanner.nextLine();
        System.out.print("Enter equipment weight (in lbs): ");
        int weight = getIntInput();
        System.out.print("Enter equipment warehouse location: ");
        String location = scanner.nextLine();        
        System.out.print("Enter equipment quantity: ");
        int quantity = getIntInput();

        Equipment eq = new Equipment(serialNumber, description, type, model, year, dimensions, weight, location, quantity, true);

        equipmentList.add(eq);
        System.out.println("Equipment added successfully!\n" + eq);
    }

    private static void listEquipment() {
        System.out.println("Listing all equipment...");
        if (equipmentList.isEmpty()) {
            System.out.println("No equipment available.");
        } else {
            for (Equipment eq : equipmentList) {
                System.out.println(eq);
            }
        }
    }

    private static void updateEquipment() {
        System.out.println("Updating equipment...");
        System.out.print("Enter equipment serial number to update: ");
        int serial = getIntInput();
        Equipment eq = findEquipmentBySerial(serial);

        if (eq != null) {
            System.out.print("Enter updated equipment description: ");
            String description = scanner.nextLine();
            System.out.print("Enter updated equipment type: ");
            String type = scanner.nextLine();
            System.out.print("Enter updated equipment model: ");
            String model = scanner.nextLine();
            System.out.print("Enter updated equipment year: ");
            int year = getIntInput();
            System.out.print("Enter updated equipment dimensions (ex: 10x20x30 in): ");
            String dimensions = scanner.nextLine();
            System.out.print("Enter updated equipment weight (in lbs): ");
            int weight = getIntInput();
            System.out.print("Enter updated equipment warehouse location: ");
            String location = scanner.nextLine();        
            System.out.print("Enter updated equipment quantity: ");
            int quantity = getIntInput();

            eq.setDescription(description);
            eq.setType(type);
            eq.setModel(model);
            eq.setYear(year);
            eq.setDimensions(dimensions);
            eq.setWeight(weight);
            eq.setLocation(location);
            eq.setQuantity(quantity);

            System.out.println("Equipment ID " + serial + " updated successfully!");
        } else {
            System.out.println("Equipment not found.");
        }
    }

    private static void removeEquipment() {
        System.out.println("Removing equipment...");
        System.out.print("Enter serial number to remove: ");
        int id = getIntInput();
        Equipment eq = findEquipmentBySerial(id);

        if (eq != null) {
            equipmentList.remove(eq);
            System.out.println("Equipment removed successfully!");
        } else {
            System.out.println("Equipment not found.");
        }
    }

    private static void searchEquipment() {
        System.out.print("Enter equipment serial number: ");
        int id = getIntInput();
        Equipment eq = findEquipmentBySerial(id);

        if (eq != null) {
            System.out.println("\nEquipment Found!");
            System.out.println(eq);
        } else {
            System.out.println("No equipment found with that serial number.");
        }
    }

    private static Equipment findEquipmentBySerial(int serial) {
        for (Equipment eq : equipmentList) {
            if (eq.getSerialNumber() == serial){
                return eq;
            }
        }
        return null;
    }

// Drone Fleet Menu (Will be implemented in the future)
    private static void droneMenu() {
        int choice;
        do {
            System.out.println("\n--- Drone Fleet Menu ---\n");
            System.out.println("1. Add Drone");
            System.out.println("2. List Drones");
            System.out.println("3. Update Drone");
            System.out.println("4. Remove Drone");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1 -> addDrone();
                case 2 -> listDrones();
                case 3 -> updateDrone();
                case 4 -> removeDrone();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addDrone() {
        System.out.println("Adding new drone...");
        System.out.print("Enter drone model: ");
        String model = scanner.nextLine();
        System.out.print("Enter drone capacity: ");
        String capacity = scanner.nextLine();
        System.out.println("Drone \"" + model + "\" with capacity \"" + capacity
                + "\" added successfully!");
    }

    private static void listDrones() {
        System.out.println("Listing all drones...");
        System.out.println("(Drone list will be displayed here)");
    }

    private static void updateDrone() {
        System.out.println("Updating drone...");
        System.out.print("Enter drone ID to update: ");
        String id = scanner.nextLine();
        System.out.println("Drone ID " + id + " updated successfully!");
    }

    private static void removeDrone() {
        System.out.println("Removing drone...");
        System.out.print("Enter drone ID to remove: ");
        String id = scanner.nextLine();
        System.out.println("Drone ID " + id + " removed successfully!");
    }

// Member Menu
    private static void memberMenu() {
        int choice;
        do {
            System.out.println("\n--- Member Information Menu ---");
            System.out.println("1. Add Member");
            System.out.println("2. View Members");
            System.out.println("3. Update Member Info");
            System.out.println("4. Remove Member");
            System.out.println("5. Search Member");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1 -> addMember();
                case 2 -> viewMembers();
                case 3 -> updateMember();
                case 4 -> removeMember();
                case 5 -> searchMember();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addMember() {
        System.out.println("Adding new member...");
        System.out.print("Enter member ID: ");
        int id = getIntInput();

        // Check for duplicate ID
        if (findMemberById(id) != null) {
            System.out.println("A member with this ID already exists!");
            return;
        }

        System.out.print("Enter member first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter member last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter member type: ");
        String type = scanner.nextLine();
        System.out.print("Enter member phone number: ");
        int phone = getIntInput();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        System.out.print("Enter member join date (YYYY-MM-DD): ");
        String joinDate = scanner.nextLine();
        System.out.print("Enter member address: ");
        String address = scanner.nextLine();
        System.out.print("Enter member distance from warehouse (miles): ");
        int distance = getIntInput();

        Member m = new Member(id, firstName, lastName, type, phone, email, joinDate, address, distance);
        
        memberList.add(m);
        System.out.println("Member added successfully!\n" + m);
    }

    private static void viewMembers() {
        System.out.println("Viewing all members...");
        if (memberList.isEmpty()) {
            System.out.println("No members registered.");
        } else {
            for (Member m : memberList) {
                System.out.println(m);
            }
        }
    }

    private static void updateMember() {
        System.out.println("Updating member information...");
        System.out.print("Enter member ID to update: ");
        int id = getIntInput();
        Member member = findMemberById(id);

        if (member != null) {
            System.out.print("Enter updated member first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter updated member last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter updated member type: ");
            String type = scanner.nextLine();
            System.out.print("Enter updated member phone number: ");
            int phone = getIntInput();
            System.out.print("Enter updated member email: ");
            String email = scanner.nextLine();
            System.out.print("Enter updated member join date (YYYY-MM-DD): ");
            String joinDate = scanner.nextLine();
            System.out.print("Enter updated member address: ");
            String address = scanner.nextLine();
            System.out.print("Enter updated member distance from warehouse (miles): ");
            int distance = getIntInput();

            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setType(type);
            member.setPhone(phone);
            member.setEmail(email);
            member.setJoinDate(joinDate);
            member.setAddress(address);
            member.setWarehouseDistance(distance);

            System.out.println("Member ID " + id + " updated successfully!");
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void removeMember() {
        System.out.println("Removing member...");
        System.out.print("Enter member ID to remove: ");
        int id = getIntInput();
        Member m = findMemberById(id);

        if (m != null) {
            memberList.remove(m);
            System.out.println("Member removed successfully!");
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void searchMember() {
        System.out.print("Enter member ID: ");
        int id = getIntInput();
        Member member = findMemberById(id);

        if (member != null) {
            System.out.println("\nMember Found!");
            System.out.println(member);
        } else {
            System.out.println("No member found with that ID.");
        }
    }

    private static Member findMemberById(int id) {
        for (Member m : memberList) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

// Rental and Delivery Options Menu (NOTE: Functionality limited for Checkpoint 2, produces warnings)
    private static void rentalMenu() {
        int choice;
        do {
            System.out.println("\n--- Rental & Delivery Menu ---");
            System.out.println("1. Rent Equipment");
            System.out.println("2. Return Equipment");
            System.out.println("3. Schedule Delivery");
            System.out.println("4. Schedule Pickup");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = getIntInput();

            switch (choice) {
                case 1 -> rentEquipment();
                case 2 -> returnEquipment();
                case 3 -> deliverEquipment();
                case 4 -> pickupEquipment();               
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void rentEquipment() {
        System.out.println("\nRenting Equipment...");


        System.out.print("Enter Member ID: ");
        int memberId = getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = getIntInput();
        System.out.print("Enter rental start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter expected return date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        System.out.println("Equipment rented successfully!");
    }

    private static void returnEquipment() {
        System.out.println("\nReturning Equipment...");

        System.out.print("Enter Member ID: ");
        int memberId = getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = getIntInput();
        System.out.print("Enter return date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        System.out.println("Equipment returned successfully!");
    }

    private static void deliverEquipment() {
        System.out.println("\nScheduling Equipment Delivery...");

        System.out.print("Enter Member ID: ");
        int memberId = getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = getIntInput();
        System.out.print("Enter Drone ID to assign: ");
        int droneId = getIntInput();
        System.out.print("Enter delivery date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.println("Equipment delivered successfully!");
    }

    private static void pickupEquipment() {
        System.out.println("\nScheduling Equipment Pickup...");

        System.out.print("Enter Member ID: ");
        int memberId = getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = getIntInput();
        System.out.print("Enter Drone ID to assign: ");
        int droneId = getIntInput();
        System.out.print("Enter pickup date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.println("Equipment returned successfully!");
    }

// Reports Menu will be implemented here in future checkpoint
    private static void reportsMenu() {
        int choice;
        do {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Option 1");
            System.out.println("2. Option 2");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1 -> System.out.println("Option 1");
                case 2 -> System.out.println("Option 2");
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

// Helper Functions
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
}
