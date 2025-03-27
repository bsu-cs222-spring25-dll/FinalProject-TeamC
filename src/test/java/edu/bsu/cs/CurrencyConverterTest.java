package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class CurrencyConverterTest {
    @Test
    public void testConvert() throws IOException {
        String base = "EUR";
        String target = "USD";
        Double amount = 1.0;

        CurrencyConverter converter = new CurrencyConverter();
        InputStream JSONdata = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.json");

        Double result = converter.convert(target,JSONdata,amount);
        Assertions.assertEquals(1.047916,result);
    }
    @Test
    public void testBigConversion() throws IOException {
        String base = "EUR";
        String target = "USD";
        Double amount = 20.0;

        CurrencyConverter converter = new CurrencyConverter();
        InputStream JSONdata = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.json");

        Double result = converter.convert(target,JSONdata,amount);
        Assertions.assertEquals(20.95832,result);
    }
}