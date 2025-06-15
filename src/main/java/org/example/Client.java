package org.example;

import java.io.Serializable;

import java.io.Serializable;

public class Client implements Serializable {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected double balance;
    protected double interestRate;

    public Client(int id, String firstName, String lastName, double balance, double interestRate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public int getId() { return id; }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        System.out.println("Balance not sufficient");
        return false;
    }

    public void applyInterest() {
        balance += balance * interestRate;
    }

    @Override
    public String toString() {
        return String.format("Client: %d, %s, Balance: %.2f, Interest: %.2f",
                id, firstName, balance, interestRate);
    }
}