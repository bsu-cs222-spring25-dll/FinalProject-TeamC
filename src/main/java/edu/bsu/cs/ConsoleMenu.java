package edu.bsu.cs;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
class ConsoleMenu {
    private final RatesParser ratesParser = new RatesParser();
    private final DecimalFormat decimalFormat = new DecimalFormat();
    private final CurrencyConverter converter = new CurrencyConverter();
    private final CallForRates ratesCaller = new CallForRates();
    Scanner scan = new Scanner(System.in);

    public void runMenu() throws IOException {
        JSONDataGetter dataGetter = new JSONDataGetter();
        APIConnection apiConnection = new APIConnection();
        String data = dataGetter.dataGetter(APIConnection.encodedUrlString());
        System.out.println("""
                ***Welcome***
                Select an option
                1. Display Exchange Rates
                2. Convert into another Currency
                3. Access Wallet
                4. Market History
                5. Quit""");
        int userInput = scan.nextInt();
        switch (userInput) {
            case 1:
                displayExchangeRates();
                break;
            case 2:
                convertToAnotherCurrency();
                break;
            case 3:
                Wallet.accessWallet();
                break;
            case 4:
                //marketHistory();
            case 5:
                break;
        }
    }


    public void displayExchangeRates() {
        try {
            System.out.println(ratesCaller.getRatesAndNames());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void convertToAnotherCurrency() {
        System.out.println("Please enter your currencies using 3 letter abbreviation, such as USD or EUR");
        System.out.println("What currency would you like to Convert from?");
        String userCurrencyFrom = scan.next();
        while(userCurrencyFrom.length() != 3) {
            System.out.println("Please enter a 3-letter currency code:");
            userCurrencyFrom = scan.next();
        }
        String currencyFrom = userCurrencyFrom.toUpperCase();

        System.out.println("What currency would you like to convert to?");
        String userCurrencyTo = scan.next();
        String currencyTo = userCurrencyTo.toUpperCase();

        System.out.println("Enter an Amount:");
        System.out.println("PLEASE ENTER AS AN A DOUBLE, SUCH AS 0.00");
        float amount = scan.nextFloat();

        List<Float> rateList;
        try {
            rateList = ratesParser.parseThroughRatesForCurrentExchangeRateList(currencyFrom, currencyTo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        float startingAmountFloat = Float.parseFloat(String.valueOf(amount));
        decimalFormat.setMaximumFractionDigits(2);
        System.out.println("Converting from " + currencyFrom + " to " + currencyTo + " with " + decimalFormat.format(startingAmountFloat) +
                " gives you " + decimalFormat.format(converter.convertUsingAmount(rateList, startingAmountFloat)) + " in " + currencyTo);
    }

    public void accessWallet() {
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

    public void accessAccount() {
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
        Wallet.displayAccountMenu(account);
    }

    private void openNewAccount() {
        System.out.print("Is this a new customer? (y/n): ");
        String choice = scan.nextLine();

        Customer customer;
        if (choice.equalsIgnoreCase("y")) {
            customer = Wallet.createNewCustomer();
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

    //public void marketHistory(){

    //}
}
