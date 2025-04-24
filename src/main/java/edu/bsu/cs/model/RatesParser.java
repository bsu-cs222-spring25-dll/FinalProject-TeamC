package edu.bsu.cs.model;

import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RatesParser {
    CallForRates rateCall = new CallForRates();
    public List<Float> parseThroughRatesForCurrentExchangeRateList(String userInputCurrency, String userOutputCurrency) throws IOException {
        String allCurrentRates = rateCall.getStringDataNoData();
        Map<String, Object> ratesMap = JsonPath.read(allCurrentRates, "$.rates");

        List<Float> rateList = new ArrayList<>();
        rateList.add(((Number)ratesMap.get(userInputCurrency)).floatValue());
        rateList.add(((Number)ratesMap.get(userOutputCurrency)).floatValue());

        return rateList;
    }
}
