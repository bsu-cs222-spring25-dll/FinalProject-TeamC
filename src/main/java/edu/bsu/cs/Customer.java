package edu.bsu.cs;

import java.io.*;
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

    public void loadCustomers(){
        Wallet wallet = new Wallet();
        String line;
        InputStream saveFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("SavedAccounts");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(saveFile))){
            while ((line = reader.readLine()) != null){
                String[] customerData = line.split(",");
                String firstName = customerData[0];
                String lastName = customerData[1];
                String pin = customerData[2];
                Customer customer = new Customer(firstName, lastName, pin);
                Wallet.addCustomer(customer);
                double balance = Double.parseDouble(customerData[3]);
                String currencyType = customerData[4];
                String accountNumber = customerData[5];
                Account account = new Account(balance, currencyType);
                customer.addAccount(account);
            }
        } catch(IOException e){
            System.out.println("Error while reading save file");
            e.printStackTrace();
        }
    }
    public void saveCustomers(){
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
