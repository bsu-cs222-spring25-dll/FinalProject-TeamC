import java.io.IOException;
import java.util.Scanner;
public class ConsoleMenu {
    Scanner scan = new Scanner(System.in);
    boolean quit = false;
    public void runMenu() throws IOException {
        JSONDataGetter dataGetter = new JSONDataGetter();
        APIConnection apiConnection = new APIConnection();
        String data = dataGetter.dataGetter(apiConnection.encodedUrlString());
        boolean quit = false;
        while(!quit) {
            System.out.println("""
                    ***Welcome***
                    Select an option
                    1. See all rates
                    2. Convert money amount
                    3. Quit""");
            choiceCase(data);
        }
    }

public void choiceCase(String data){
    int userInput = scan.nextInt();
    switch (userInput) {
        case 1:
            System.out.println(data);
            break;
        case 2:
            System.out.println("");
            break;
        case 3:
            quit = true;
        }


    }
}
