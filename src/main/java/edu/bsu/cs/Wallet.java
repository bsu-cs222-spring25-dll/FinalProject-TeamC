package edu.bsu.cs;

import java.io.*;
import java.util.ArrayList;

public class Wallet {
    private static final ArrayList<Customer> customerArrayList = new ArrayList<>();

    public static void addCustomer(Customer customer) {
        customerArrayList.add(customer);
    }
    public boolean removeCustomer(Customer customer) {
        return customerArrayList.remove(customer);
    }

    // Returns the customer if found by account PIN, else returns null
    public static Customer getCustomer(String pin) {
        for (Customer customer : customerArrayList) {
            if (customer.getPin().equals(pin)) {
                return customer;
            }
        }
        return null;
    }

    public void saveCustomer(){

    }

    public void loadCustomer() throws IOException {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader("SavedAccounts"))){
            while ((line = reader.readLine()) != null){
                String[] customerData = line.split(",");
                String firstName = customerData[0];
                String lastName = customerData[1];
                String pin = customerData[2];
                Customer customer = new Customer(firstName, lastName, pin);
                addCustomer(customer);
            }
        }
    }

}