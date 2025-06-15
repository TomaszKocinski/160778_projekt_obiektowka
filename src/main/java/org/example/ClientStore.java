package org.example;

import java.io.*;
import java.util.*;

public class ClientStore {
    private List<Client> clients = new ArrayList<>();
    private final String fileName = "clients.dat";

    public void save() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(clients);
            System.out.println("Changes saved to file.");
        }
    }

    public void load() throws IOException, ClassNotFoundException {
        File f = new File(fileName);
        if (f.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
                clients = (List<Client>) in.readObject();
                System.out.printf("File contains %d users\n", clients.size());
                for (Client c : clients) {
                    System.out.printf("User ID %d Balance %.2f\n", c.getId(), c.balance);
                }
            }
        }
    }

    public List<Client> getClients() {
        return clients;
    }
}