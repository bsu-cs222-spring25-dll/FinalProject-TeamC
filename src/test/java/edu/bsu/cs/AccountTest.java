package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {
    @Test
    public void accountDepositTest() {
        Account testAccount = new Account(500, "EUR", "0001");
        testAccount.deposit(500);

        Assertions.assertEquals(1000, testAccount.getBalance());
    }

    @Test
    public void accountWithdrawTest() {
        Account testAccount = new Account(500, "EUR", "0002");
        testAccount.withdraw(250);

        Assertions.assertEquals(250, testAccount.getBalance());
    }


}
