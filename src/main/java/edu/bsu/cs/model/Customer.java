package edu.bsu.cs.model;

import java.io.*;
import java.util.ArrayList;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String pin;
    private static ArrayList<Account> accountList = null;


    public Customer(String firstName, String lastName, String pin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        accountList = new ArrayList<>();
    }


    public void addAccount(Account account) {
        accountList.add(account);
    }


    public Account getAccount(String accountNumber) {
        for (Account account : accountList) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }


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

    public static void loadCustomers(){
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader("SavedCustomers.txt"))){
            while ((line = reader.readLine()) != null){
                String[] data = line.split(",");
                String firstName = data[0];
                String lastName = data[1];
                String pin = data[2];
                Customer customer = new Customer(firstName, lastName,pin);
                Wallet.addCustomer(customer);

                loadAccount(customer);
            }
        } catch (IOException e) {
            System.out.println("Error loading customers");
        }
    }

    public static void loadAccount(Customer customer){
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader("SavedAccounts.txt"))){
            while ((line = reader.readLine()) != null){
                String[] data = line.split(",");
                String pin = data[0];
                double balance = Double.parseDouble(data[1]);
                String currencyType = data[2];

                if (pin.equals(customer.getPin())){
                    Account account = new Account(balance, currencyType, customer.getPin());
                    customer.addAccount(account);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading accounts");
        }
    }

    public static void saveCustomers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SavedCustomers.txt"))){
            ArrayList<Customer> customers = Wallet.getCustomerArrayList();

            for (Customer line : customers){
                writer.write(String.join(",",line.getFirstName(),line.getLastName(),line.getPin()));
                writer.newLine();
            }

            saveAccounts();

        } catch (IOException e) {
            System.out.println("ERROR Saving customer");
        }
    }

    public static void saveAccounts() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SavedAccounts.txt"))){
            for(Account line : accountList){
                writer.write(String.join(",",line.getAccountPin(),Double.toString(line.getBalance()),line.getAccountCurrencyType()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("ERROR saving customer accounts");
        }
    }




    @Override
    public String toString() {
        return String.format("Name: %s %s\nPin: %s", firstName, lastName, pin);
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getPin() {
        return pin;
    }
    public ArrayList<Account> getAccountList() {
        return accountList;
    }

}
