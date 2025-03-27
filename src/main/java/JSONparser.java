import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JSONparser {
    public double parse(String target, InputStream JSONdata) throws IOException {
        JSONArray value = JsonPath.read(JSONdata, "$.." + target);
        String conversionString = value.getFirst().toString();
        return Double.parseDouble(conversionString);
    }
//    public String CurrencyParser(InputStream JSONdata, RatesParser rateList){
//        List<Currency> currencyList = rateList.parseThroughRatesForCurrentExchangeRateList()
//        for(int i = 0; i < rateList(); i++)
//            Currency currency = new Currency();
//    }
//    public JSONArray parseCurrencyName(InputStream JSONData)throws IOException{
//        return JsonPath.read(JSONData, "$..rates");
//    }
}
