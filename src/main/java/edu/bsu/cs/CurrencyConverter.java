package edu.bsu.cs;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CurrencyConverter {
    public double convert(String target, InputStream JSONdata, Double amount) throws IOException {
        JSONparser parser = new JSONparser();
        Double conversionValue = parser.parse(target,JSONdata);
        return amount * conversionValue;
    }

    public float convertUsingAmount(List<Float> rateList, float startingAmout) {
        float exchangeRate = (rateList.get(1) / rateList.get(0));
        float endingAmount = (startingAmout * exchangeRate);
        String formattedAmount = String.format("%.2f", endingAmount);
        return Float.parseFloat(formattedAmount);
    }

}