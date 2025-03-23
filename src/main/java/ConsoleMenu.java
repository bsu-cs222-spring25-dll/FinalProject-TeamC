import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
public class ConsoleMenu {
    private final RatesParser ratesParser = new RatesParser();
    private final DecimalFormat decimalFormat = new DecimalFormat();
    private final CurrencyConverter converter = new CurrencyConverter();
    public void runMenu() throws IOException {
        JSONDataGetter dataGetter = new JSONDataGetter();
        APIConnection apiConnection = new APIConnection();
        String data = dataGetter.dataGetter(apiConnection.encodedUrlString());
        Scanner scan = new Scanner(System.in);
        System.out.println("""
                ***Welcome***
                Select an option
                1. Display Exchange Rates
                2. Convert into another Currency
                3. Quit""");
        int userInput = scan.nextInt();
        switch (userInput) {
            case 1:
                System.out.println(data);
                break;
            case 2:
                System.out.println("Please enter your currencies using 3 letter abbreviation, such as USD or EUR");
                System.out.println("What currency would you like to Convert from?");
                String userCurrencyFrom = scan.next();
                String currencyFrom = userCurrencyFrom.toUpperCase();

                System.out.println("What currency would you like to convert to?");
                String userCurrencyTo = scan.next();
                String currencyTo = userCurrencyTo.toUpperCase();

                System.out.println("Enter an Amount:");
                System.out.println("PLEASE ENTER AS AN A DOUBLE, SUCH AS 0.00");
                float amount = scan.nextFloat();

                List<Float> rateList = ratesParser.parseThroughRatesForCurrentExchangeRateList(currencyFrom, currencyTo );
                float startingAmountFloat = Float.parseFloat(String.valueOf(amount));
                decimalFormat.setMaximumFractionDigits(2);
                System.out.println("Converting from " + currencyFrom + " to " + currencyTo + " with " + decimalFormat.format(startingAmountFloat) +
                        " gives you " + decimalFormat.format(converter.convertUsingAmount(rateList, startingAmountFloat)) + " in " + currencyTo);
                break;

        }
    }
}