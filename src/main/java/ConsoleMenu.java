import java.io.IOException;
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
        switch (userInput){
            case 1:
                System.out.println(data);
        }

    }
}
