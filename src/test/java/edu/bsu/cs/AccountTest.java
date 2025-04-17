package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {
    @Test
    public void accountDepositTest() {
        Account testAccount = new Account(500, "EUR");
        testAccount.deposit(500);

        Assertions.assertEquals(1000, testAccount.getBalance());
    }

    @Test
    public void accountWithdrawTest() {
        Account testAccount = new Account(500, "EUR");
        testAccount.withdraw(250);

        Assertions.assertEquals(250, testAccount.getBalance());
    }


}
