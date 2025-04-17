package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    @Test
    public void customerAccountCreationTest() {
        Customer testCustomer = new Customer("Christian", "Johnson", "1234");
        Account newAccount = new Account(500, "USD");

        testCustomer.addAccount(newAccount);

        Assertions.assertEquals(1, testCustomer.getAccountListAsNumber());
    }
}
