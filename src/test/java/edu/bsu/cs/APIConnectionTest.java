import edu.bsu.cs.APIConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLConnection;

public class APIConnectionTest {
    @Test
    public void encodedUrlTest() throws IOException {
        String base = "EUR";
        URLConnection connection = APIConnection.encodedUrlString();
        Assertions.assertNotNull(connection);
    }
}