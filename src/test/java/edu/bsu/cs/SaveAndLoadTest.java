package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SaveAndLoadTest {

    @Test
    public void saveCustomerTest() throws IOException {
        Customer.loadCustomers();

        Customer customer = new Customer("Test", "Test", "2615");
        Account account = new Account (420.69, "EUR", "2615");
        customer.addAccount(account);
        Wallet.addCustomer(customer);

        Customer.saveCustomers();

        String line;
        String read = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("SavedCustomers.txt"))){
            while ((line = reader.readLine()) != null){
                String[] data = line.split(",");

                if (data[2].equals("2615")){
                   read = line;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers");
        }
        Assertions.assertEquals("Test,Test,2615", read);
    }

    @Test
    public void saveAccountTest(){
        Customer.loadCustomers();

        Customer customer = new Customer("Test", "Test", "2615");
        Account account = new Account (420.69, "EUR", "2615");
        customer.addAccount(account);
        Wallet.addCustomer(customer);

        Customer.saveCustomers();

        String line;
        String read = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("SavedAccounts.txt"))){
            while ((line = reader.readLine()) != null){
                String[] data = line.split(",");

                if (data.length > 1){
                    if (data[0].equals("2615")){
                        read = line;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers");
        }
        Assertions.assertEquals("2615,420.69,EUR", read );
    }
}
