package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class RatesParserTest {
    RatesParser ratesParser = new RatesParser();

    @Test
    public void parseThroughRatesForCurrentExchangeRateListTest() throws IOException {
        List<Float> rateList = ratesParser.parseThroughRatesForCurrentExchangeRateList("USD", "CAD");
        Assertions.assertNotNull(rateList);
    }

}
