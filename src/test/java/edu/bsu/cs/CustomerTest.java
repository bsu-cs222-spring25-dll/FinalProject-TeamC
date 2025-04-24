package edu.bsu.cs;

import edu.bsu.cs.model.Account;
import edu.bsu.cs.model.Customer;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    @Test
    public void customerAccountCreationTest() {
        Customer testCustomer = new Customer("Christian", "Johnson", "1234");
        Account newAccount = new Account(500, "USD", "0003");

        testCustomer.addAccount(newAccount);

        //Assertions.assertEquals(1, testCustomer.getAccountListAsNumber());
    }
}
