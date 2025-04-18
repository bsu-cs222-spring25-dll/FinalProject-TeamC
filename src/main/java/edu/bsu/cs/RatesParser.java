package edu.bsu.cs;

import net.minidev.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RatesParser {
    CallForRates rateCall = new CallForRates();
    JSONToFloat jsonToFloat = new JSONToFloat();
    public List<Float> parseThroughRatesForCurrentExchangeRateList(String userInputCurrency, String userOutputCurrency) throws IOException {
        JSONArray exchangeRateValueConvertingFrom = rateCall.getRateAsJSONArray(userInputCurrency);
        JSONArray exchangeRateValueConvertingTo = rateCall.getRateAsJSONArray(userOutputCurrency);
        Float startingRateFloat = jsonToFloat.jsonArrayToFloat(exchangeRateValueConvertingFrom);
        Float endingRateFloat = jsonToFloat.jsonArrayToFloat(exchangeRateValueConvertingTo);

        List<Float> rateList = new ArrayList<>();
        rateList.add(startingRateFloat);
        rateList.add(endingRateFloat);
        return rateList;
    }
    public List<String> parseAvailableCurrencies(String ratesData) {
        List<String> currencies = new ArrayList<>();

        String[] lines = ratesData.split("\n");
        for (String line : lines) {
            if (line.contains(":")) {
                String currency = line.split(":")[0].trim();
                currencies.add(currency);
            }
        }

        return currencies;
    }
}
