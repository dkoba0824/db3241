package menus;

import entities.Member;
import utilities.Utilities;

import java.util.ArrayList;
import java.util.Scanner;

public class MemberMenu {

    private static final Scanner scanner = new Scanner(System.in);

    // Local array (Checkpoint 2 functionality)
    // THIS WILL BE REPLACED WITH SQL IN FUTURE CHECKPOINTS
    private static ArrayList<Member> memberList = new ArrayList<>();

    // Member Menu
    public static void memberMenu() {
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

            choice = Utilities.getIntInput();

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
        int id = Utilities.getIntInput();

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
        int phone = Utilities.getIntInput();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        System.out.print("Enter member join date (YYYY-MM-DD): ");
        String joinDate = scanner.nextLine();
        System.out.print("Enter member address: ");
        String address = scanner.nextLine();
        System.out.print("Enter member distance from warehouse (miles): ");
        int distance = Utilities.getIntInput();

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
        int id = Utilities.getIntInput();
        Member member = findMemberById(id);

        if (member != null) {
            System.out.print("Enter updated member first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter updated member last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter updated member type: ");
            String type = scanner.nextLine();
            System.out.print("Enter updated member phone number: ");
            int phone = Utilities.getIntInput();
            System.out.print("Enter updated member email: ");
            String email = scanner.nextLine();
            System.out.print("Enter updated member join date (YYYY-MM-DD): ");
            String joinDate = scanner.nextLine();
            System.out.print("Enter updated member address: ");
            String address = scanner.nextLine();
            System.out.print("Enter updated member distance from warehouse (miles): ");
            int distance = Utilities.getIntInput();

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
        int id = Utilities.getIntInput();
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
        int id = Utilities.getIntInput();
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
}
