package edu.bsu.cs;

import java.util.ArrayList;

public class Customer {
    private String firstName;
    private String lastName;
    private String pin;
    private ArrayList<Account> accountList;

    // Constructor
    public Customer(String firstName, String lastName, String pin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.accountList = new ArrayList<>();
    }

    // Method to add an account to the customer
    public void addAccount(Account account) {
        accountList.add(account);
    }


    // Method to get the details of an account
    public Account getAccount(String accountNumber) {
        for (Account account : accountList) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }


    // Method to get the details of all the accounts of a customer
    public String getAllAccounts() {
        if (accountList.isEmpty()) {
            return "No accounts found";
        }
        StringBuilder builder = new StringBuilder();
        for (Account account : accountList) {
            builder.append(account.toString()).append("\n");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s\nPin: %s", firstName, lastName, pin);
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    public boolean matchPin(String pin) {
        if (pin.equals(this.pin)) {
            return true;
        }
        return false;
    }

    public ArrayList<Account> getAccountList() {
        return accountList;
    }
}
