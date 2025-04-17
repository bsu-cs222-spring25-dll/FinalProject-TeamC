package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WalletTest {
    @Test
    public void walletCustomerCreationTest() {
        Customer testCustomer1 = new Customer("test", "test", "1234");
        Customer testCustomer2 = new Customer("hi", "ji", "1234");
        Wallet.addCustomer(testCustomer1);
        Wallet.addCustomer(testCustomer2);

        Assertions.assertEquals(2, Wallet.getSizeOfCustomerList());
    }
}
