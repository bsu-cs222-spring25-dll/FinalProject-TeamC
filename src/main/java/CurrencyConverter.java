import java.io.IOException;
import java.io.InputStream;

public class CurrencyConverter {
    public double convert(String target, InputStream JSONdata, Double amount) throws IOException {
        JSONparser parser = new JSONparser();
        Double conversionValue = parser.parse(target,JSONdata);
        Double conversion  = amount * conversionValue;
        return conversion;
    }
}