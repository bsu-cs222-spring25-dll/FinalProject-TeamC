package edu.bsu.cs;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.net.URLConnection;

public class CallForRates {
    JSONDataGetter dataGetter = new JSONDataGetter();

    public JSONArray getRateAsJSONArray(String currency) throws IOException {
        String allCurrentRates = getStringDataNoData();
        return JsonPath.read(allCurrentRates, "$.." + currency);
    }
    public String getRatesAndNames() throws IOException {
        String allCurrentRates = getStringDataNoData();
        String rates = JsonPath.read(allCurrentRates, "$..rates").toString();
        rates = rates.replace("{","");
        rates = rates.replace("}","");
        rates = rates.replace("[","");
        rates = rates.replace("]","");
        rates = rates.replace("\"","");
        return rates.replace(",","\n");
    }


    public String getStringDataNoData() throws IOException {
        URLConnection connection = APIConnection.encodedUrlString();
        return dataGetter.dataGetter(connection);
    }
}