package edu.bsu.cs.model;

import com.jayway.jsonpath.JsonPath;
import edu.bsu.cs.utility.JSONToFloat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RatesParser {
    CallForRates rateCall = new CallForRates();
    JSONToFloat jsonToFloat = new JSONToFloat();
    public List<Float> parseThroughRatesForCurrentExchangeRateList(String userInputCurrency, String userOutputCurrency) throws IOException {
        String allCurrentRates = rateCall.getStringDataNoData();
        Map<String, Object> ratesMap = JsonPath.read(allCurrentRates, "$.rates");

        List<Float> rateList = new ArrayList<>();
        rateList.add(((Number)ratesMap.get(userInputCurrency)).floatValue());
        rateList.add(((Number)ratesMap.get(userOutputCurrency)).floatValue());

        return rateList;
    }
    public List<String> parseAvailableCurrencies(String ratesData) {
        List<String> currencies = new ArrayList<>();

        try {
            Map<String, Object> ratesMap = JsonPath.read(ratesData, "$.rates");
            currencies.addAll(ratesMap.keySet());
        } catch (Exception e) {
            currencies = List.of("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CNY", "INR", "MXN");
        }

        return currencies;
    }
}
