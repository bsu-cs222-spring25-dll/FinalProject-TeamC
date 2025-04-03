package edu.bsu.cs;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;

public class JSONparser {
    public double parse(String target, InputStream JSONdata) throws IOException {
        JSONArray value = JsonPath.read(JSONdata, "$.." + target);
        String conversionString = value.getFirst().toString();
        return Double.parseDouble(conversionString);
    }
//    public String CurrencyParser(InputStream JSONdata, edu.bsu.cs.RatesParser rateList){
//        List<edu.bsu.cs.Currency> currencyList = rateList.parseThroughRatesForCurrentExchangeRateList()
//        for(int i = 0; i < rateList(); i++)
//            edu.bsu.cs.Currency currency = new edu.bsu.cs.Currency();
//    }
//    public JSONArray parseCurrencyName(InputStream JSONData)throws IOException{
//        return JsonPath.read(JSONData, "$..rates");
//    }
}
