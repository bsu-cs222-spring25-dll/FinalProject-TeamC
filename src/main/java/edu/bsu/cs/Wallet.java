package edu.bsu.cs;

import java.util.ArrayList;
import java.util.Scanner;

public class Wallet {
    static Scanner scan = new Scanner(System.in);
    private static ArrayList<Customer> customerArrayList = new ArrayList<>();

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
    public static void accessWallet() {
        while (true) {
            System.out.println("\n--- Wallet Menu ---");
            System.out.println("1) Access Account");
            System.out.println("2) Open a New Account");
            System.out.println("3) Exit");

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    accessAccount();
                    break;
                case 2:
                    openNewAccount();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid entry. Please select a valid option.");
            }
        }
    }
    public static void accessAccount() {
        System.out.print("Enter your PIN: ");
        String pin = scan.nextLine();
        Customer customer = Wallet.getCustomer(pin);

        if (customer == null) {
            System.out.println("PIN is not valid.");
            return;
        }

        System.out.println("Your Accounts: ");
        System.out.println(customer.getAllAccounts());
        System.out.print("Enter the account number you want to access: ");
        String accountNumber = scan.nextLine();
        Account account = customer.getAccount(accountNumber);

        if (account == null) {
            System.out.println("Account number invalid.");
            return;
        }
        displayAccountMenu(account);
    }

    private static void openNewAccount() {
        System.out.print("Is this a new customer? (y/n): ");
        String choice = scan.nextLine();

        Customer customer;
        if (choice.equalsIgnoreCase("y")) {
            customer = createNewCustomer();
        } else {
            System.out.print("Enter your PIN: ");
            String pin = scan.nextLine();
            customer = Wallet.getCustomer(pin);

            if (customer == null) {
                System.out.println("Invalid PIN.");
                return;
            }
        }

        System.out.print("Enter the initial deposit amount: ");
        double initialDeposit = Double.parseDouble(scan.nextLine());
        System.out.print("Enter the account currency type (3-letter code): ");
        String accountCurrencyType = scan.nextLine();
        Account newAccount = new Account(initialDeposit, accountCurrencyType);
        customer.addAccount(newAccount);
        System.out.println("New Account Number: " + newAccount.getAccountNumber());
    }
    static void displayAccountMenu(Account account) {
        while (true) {
            System.out.println("\n--- Account Menu ---");
            System.out.println("1) Deposit");
            System.out.println("2) Withdraw");
            System.out.println("3) Exit");

            int choice = Integer.parseInt(scan.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter the amount to deposit: ");
                    double depositAmount = Double.parseDouble(scan.nextLine());
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter the amount to withdraw: ");
                    double withdrawalAmount = Double.parseDouble(scan.nextLine());
                    account.withdraw(withdrawalAmount);
                    break;
                case 3:
                    return; // Exit back to Main Menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    static Customer createNewCustomer() {
        System.out.print("Enter first name: ");
        String firstName = scan.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scan.nextLine();
        System.out.print("Create a PIN: ");
        String pin = scan.nextLine();

        Customer customer = new Customer(firstName, lastName, pin);
        Wallet.addCustomer(customer);
        System.out.println("New customer created.");
        return customer;
    }
}