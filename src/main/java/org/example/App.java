package org.example;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        ClientStore store = new ClientStore();
        store.load();
        ClientService service = new ClientService(store);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("1. Add client");
                System.out.println("2. Deposit to account");
                System.out.println("3. Withdraw from account");
                System.out.println("4. Transfer between accounts");
                System.out.println("5. Apply interest");
                System.out.println("6. Show all clients");
                System.out.println("7. Find client by ID");
                System.out.println("8. Delete client");
                System.out.println("0. Exit");
                System.out.print("Choose option: ");

                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> addClient(scanner, service);
                    case 2 -> deposit(scanner, service, store);
                    case 3 -> withdraw(scanner, service, store);
                    case 4 -> transfer(scanner, service);
                    case 5 -> applyInterest(scanner, service, store);
                    case 6 -> service.showAllClients();
                    case 7 -> findClient(scanner, service);
                    case 8 -> deleteClient(scanner, service);
                    case 0 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // clear buffer
            }
        }
    }

    static void addClient(Scanner scanner, ClientService service) throws Exception {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        if (service.isClientExisting(id)){
            System.out.print("User is already existing, aborting adding a client");
            return;
        }
        System.out.print("First name: ");
        String first = scanner.next();
        System.out.print("Last name: ");
        String last = scanner.next();
        System.out.print("Balance: ");
        double bal = scanner.nextDouble();
        System.out.print("Interest (comma separated, example: 0,02): ");
        double interest = scanner.nextDouble();
        System.out.print("VIP? (y/n): ");
        String vip = scanner.next();
        if (vip.equalsIgnoreCase("y")) {
            System.out.print("Extra interest (comma separated, example: 0,02): ");
            double extra = scanner.nextDouble();
            service.addClient(new VIPClient(id, first, last, bal, interest, extra));
        } else {
            service.addClient(new Client(id, first, last, bal, interest));
        }
    }

    static void deposit(Scanner scanner, ClientService service, ClientStore store) throws Exception {
        System.out.print("Client ID: ");
        int id = scanner.nextInt();
        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        Client c = service.findClient(id);
        if (c != null) {
            c.deposit(amount);
            store.save();
        }
    }

    static void withdraw(Scanner scanner, ClientService service, ClientStore store) throws Exception {
        System.out.print("Client ID: ");
        int id = scanner.nextInt();
        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        Client c = service.findClient(id);
        if (c != null && c.withdraw(amount)) {
            store.save();
        }
    }

    static void transfer(Scanner scanner, ClientService service) throws Exception {
        System.out.print("From ID: ");
        int from = scanner.nextInt();
        System.out.print("To ID: ");
        int to = scanner.nextInt();
        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        service.transfer(from, to, amount);
    }

    static void applyInterest(Scanner scanner, ClientService service, ClientStore store) throws Exception {
        System.out.print("Client ID: ");
        int id = scanner.nextInt();
        Client c = service.findClient(id);
        if (c != null) {
            c.applyInterest();
            store.save();
        }
    }

    static void findClient(Scanner scanner, ClientService service) {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        Client c = service.findClient(id);
        if (c != null) System.out.println(c);

    }

    static void deleteClient(Scanner scanner, ClientService service) throws Exception {
        System.out.print("ID to delete: ");
        int id = scanner.nextInt();
        boolean result = service.removeClient(id);
        if (result) {
            System.out.println("Client removed.");
        } else {
            System.out.println("Client not found.");
        }
    }
}
