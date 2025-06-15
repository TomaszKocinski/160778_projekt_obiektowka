package org.example;

public class VIPClient extends Client {
    private double extraInterestRate;

    public VIPClient(int id, String firstName, String lastName, double balance, double interestRate, double extraInterestRate) {
        super(id, firstName, lastName, balance, interestRate);
        this.extraInterestRate = extraInterestRate;
    }

    @Override
    public void applyInterest() {
        balance += balance * (interestRate + extraInterestRate);
    }

    @Override
    public String toString() {
        return String.format("VIPClient: %d, %s, Balance: %.2f, Interest: %.2f, Extra: %.2f",
                id, firstName, balance, interestRate, extraInterestRate);
    }
}
