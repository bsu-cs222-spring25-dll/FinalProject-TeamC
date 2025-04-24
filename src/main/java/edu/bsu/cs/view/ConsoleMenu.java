package edu.bsu.cs.view;

import edu.bsu.cs.model.*;

import java.io.FileNotFoundException;
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

    public void runMenu() {
        Customer.loadCustomers();
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
                marketHistory();
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
    public void marketHistory(){

    }
}
