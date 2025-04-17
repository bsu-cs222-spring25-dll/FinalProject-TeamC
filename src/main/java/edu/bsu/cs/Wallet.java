package edu.bsu.cs;

import java.util.ArrayList;

public class Wallet {
    private static ArrayList<Customer> customerArrayList;

    public Wallet() {
        customerArrayList = new ArrayList<>();
    }

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

}