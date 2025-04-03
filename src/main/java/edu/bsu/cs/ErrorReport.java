package edu.bsu.cs;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;


import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ErrorReport {

    public String chechkConnectionStatus(URL url) {
        try {
            url.openConnection().connect();
        } catch (Exception NetworkError) {
            return "There was a network error; could not connect to the internet";
        }
        return "";
    }

    public boolean checkEmptyInput(String input) {
        return input.isEmpty();
    }

    public boolean checkSupportedCurrency(String currency) throws IOException {
        APIConnection APIConnection = new APIConnection();
        JSONDataGetter dataGetter = new JSONDataGetter();

        URLConnection connection = APIConnection.encodedUrlString();
        String allCurrentRates = dataGetter.dataGetter(connection);
        JSONArray checkForSupportedCurrency = JsonPath.read(allCurrentRates, "$.." + currency);
        return checkForSupportedCurrency.isEmpty();
    }

    public boolean checkInputAmountCanBeFloat(String inputAmount) {
        try {
            Float.parseFloat(inputAmount);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }

    public String check429Status(int responseCode) {
        if (responseCode == 429) {
            return "Your API Key is full, please change the api key";
        }
        else {
            return "";
        }
    }
}
