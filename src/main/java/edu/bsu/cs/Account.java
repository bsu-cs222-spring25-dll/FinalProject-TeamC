package edu.bsu.cs;

public class Account {
    private double balance ;
    private String accountCurrencyType;
    private String accountNumber;
    private String pin;
    private static int numOfAccounts = 1000;

    public Account(double initialDeposit, String accountCurrencyType, String pin) {
        this.balance = initialDeposit;
        this.accountCurrencyType = accountCurrencyType;
        this.accountNumber = "" + numOfAccounts;
        this.pin = pin;
        numOfAccounts++;
    }


    public void deposit(double depositAmount) {
        if (depositAmount > 0) {
            balance += depositAmount;
            System.out.println("Deposited " + depositAmount);
            System.out.println("Updated balance: " + balance + accountCurrencyType);
        } else{
            System.out.println("Invalid Deposit");
        }
    }

    public void withdraw(double withdrawAmount) {
        if (withdrawAmount > balance) {
            System.out.println("Insufficient Balance. Withdrawal exceeds balance");
        } else if (withdrawAmount > 0) {
            balance -= withdrawAmount;
            System.out.println("Withdrawn " + withdrawAmount);
            System.out.println("Updated balance: " + balance + accountCurrencyType);
        }else {
            System.out.println("Invalid Amount");
        }
    }

    @Override
    public String toString() {
        return String.format("Balance: %.2f " + accountCurrencyType + "\nAccount Number: %s",
                balance, accountNumber);

    }
    public double getBalance() {
        return balance;
    }
    public String getAccountCurrencyType(){return accountCurrencyType;}
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountPin(){return pin; }
}