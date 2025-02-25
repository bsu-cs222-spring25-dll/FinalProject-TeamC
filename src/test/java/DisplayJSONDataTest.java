import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLConnection;

public class DisplayJSONDataTest {
    @Test
    public void testDisplayJSONData() throws IOException {
        String jSONData = APIConnection.getData("http://api.exchangeratesapi.io/v1/latest?access_key=91c58f29bd36a7471e286d9ef212bc6a&format=1");
        DisplayJSONData displayJSONData = new DisplayJSONData();
        URLConnection connection = APIConnection.encodedUrlString();
        String testResult = displayJSONData.dataGetter(connection);
        Assertions.assertEquals();
    }
}
