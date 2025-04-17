package edu.bsu.cs;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CallForRatesTest {
    CallForRates APICaller = new CallForRates();

    @Test
    public void getRateAsJSONArrayNoDateTest() throws IOException {
        String currency = "USD";
        JSONArray testValue = APICaller.getRateAsJSONArray(currency);
        Assertions.assertNotNull(testValue);
    }
}
