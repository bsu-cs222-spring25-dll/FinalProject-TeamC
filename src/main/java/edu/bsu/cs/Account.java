package edu.bsu.cs;

public class Account {
    private double balance ;
    private String accountNumber;
    private static int numOfAccounts = 1000;

    public Account(double initialDeposit) {
        this.balance = initialDeposit;
        this.accountNumber = "" + numOfAccounts;
        numOfAccounts++;
    }


    public void deposit(double depositAmount) {
        if (depositAmount > 0) {
            balance += depositAmount;
            System.out.println("Deposited " + depositAmount);
            System.out.println("Updated balance: " + balance);
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
            System.out.println("Updated balance: " + balance);
        }else {
            System.out.println("Invalid Amount");
        }
    }

    @Override
    public String toString() {
        return String.format("Balance: %.2f\nAccount Number: %s\n",
                balance, accountNumber);
    }
    public double getBalance() {
        return balance;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public static int getNumOfAccounts() {
        return numOfAccounts;
    }
}