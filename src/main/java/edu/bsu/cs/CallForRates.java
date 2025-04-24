package edu.bsu.cs;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Map;

public class CallForRates {
    JSONDataGetter dataGetter = new JSONDataGetter();

    public JSONArray getRateAsJSONArray(String currency) throws IOException {
        String allCurrentRates = getStringDataNoData();
        return JsonPath.read(allCurrentRates, "$.." + currency);
    }
    public String getRatesAndNames() throws IOException {
        String allCurrentRates = getStringDataNoData();

        try {
            Map<String, Object> ratesMap = JsonPath.read(allCurrentRates, "$.rates");
            String baseCurrency = JsonPath.read(allCurrentRates, "$.base");

            StringBuilder formattedRates = new StringBuilder();
            formattedRates.append("Base Currency: ").append(baseCurrency).append("\n\n");

            ratesMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        formattedRates.append(entry.getKey())
                                .append(": ")
                                .append(entry.getValue())
                                .append("\n");
                    });

            return formattedRates.toString();

        } catch (Exception e) {
            try {
                Map<String, Object> ratesMap = JsonPath.read(allCurrentRates, "$.rates");
                return allCurrentRates;
            } catch (Exception ex) {
                throw new IOException("Invalid JSON response from API");
            }
        }
    }


    public String getStringDataNoData() throws IOException {
        URLConnection connection = APIConnection.encodedUrlString();
        String response = dataGetter.dataGetter(connection);

        try {
            Object parsed = JsonPath.parse(response).json();
            return response;
        } catch (Exception e) {
            throw new IOException("Invalid JSON response from API");
        }
    }
}