import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
public class ConsoleMenu {
    public void runMenu() throws IOException {
        JSONDataGetter dataGetter = new JSONDataGetter();
        APIConnection apiConnection = new APIConnection();
        String data = dataGetter.dataGetter(apiConnection.encodedUrlString());
        Scanner scan = new Scanner(System.in);
        System.out.println("""
                ***Welcome***
                Select an option
                1. See all rates
                2. Convert money amount
                3. Quit""");
        int userInput = scan.nextInt();
        switch (userInput) {
            case 1:
                System.out.println(data);
            case 2:
                System.out.println("Please enter your currencies using 3 digits, such as USD or EUR");
                System.out.println("What currency would you like to Convert from?");
                String baseResponse = scan.next();
                String base = baseResponse.toUpperCase();

                System.out.println("What currency would you like to convert to?");
                String targetResponse = scan.next();
                String target = targetResponse.toUpperCase();

                System.out.println("How much " + base + " would you like to convert to " + target + "?");
                System.out.println("PLEASE ENTER AS AN A DOUBLE, SUCH AS 0.00");
                double amount = scan.nextDouble();

                System.out.println("Base: " + base + " Target: " + target + " Amount " + amount);



        }

    }
}
