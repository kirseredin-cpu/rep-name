package ru.bulgakov.java2.package1;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String ownerName;
    private double balance;

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Баланс не может быть отрицательным");
        }
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public static void main(String[] args) {
        BankAccount balance1 = new BankAccount();
        balance1.setBalance(1000);
        balance1.setOwnerName("Albert");
        balance1.setBalance(-500);

        System.out.println(balance1.getBalance());
    }
}
