package org.example;

import java.io.IOException;

public class ClientService {
    private final ClientStore store;

    public ClientService(ClientStore store) {
        this.store = store;
    }

    public void addClient(Client c) throws IOException {
        store.getClients().add(c);
        store.save();
    }

    public Client findClient(int id) {
        for (Client c : store.getClients()) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public boolean isClientExisting(int id) {
        return findClient(id) != null;
    }

    public boolean transfer(int fromId, int toId, double amount) throws IOException {
        Client from = findClient(fromId);
        Client to = findClient(toId);
        if (from != null && to != null && from.withdraw(amount)) {
            to.deposit(amount);
            store.save();
            return true;
        }
        System.out.println("Coudln't find the clients");
        return false;
    }

    public boolean removeClient(int id) throws IOException {
        boolean result = store.getClients().removeIf(c -> c.getId() == id);
        if (result) {
            store.save();
            System.out.printf("Removed a client with id: %d%n", id);
        }
        return result;
    }

    public void showAllClients() {
        for (Client c : store.getClients()) {
            System.out.println(c);
        }
    }
}
