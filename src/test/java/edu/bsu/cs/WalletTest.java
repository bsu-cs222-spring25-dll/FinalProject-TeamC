package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WalletTest {
    @Test
    public void walletCustomerCreationTest() {
        Customer testCustomer1 = new Customer("test", "test", "2348");
        Customer testCustomer2 = new Customer("hi", "ji", "7865");
        Wallet.addCustomer(testCustomer1);
        Wallet.addCustomer(testCustomer2);

        Customer result1 = Wallet.getCustomer("2348");
        Customer result2 = Wallet.getCustomer("7865");

        Assertions.assertEquals(testCustomer1,result1);
        Assertions.assertEquals(testCustomer2, result2);
    }
}
