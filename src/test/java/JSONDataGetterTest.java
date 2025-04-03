import edu.bsu.cs.APIConnection;
import edu.bsu.cs.JSONDataGetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLConnection;

public class JSONDataGetterTest {
    @Test
    public void dataGetterTest() throws IOException {
        JSONDataGetter dataGetter = new JSONDataGetter();
        String base = "EUR";
        URLConnection connection = APIConnection.encodedUrlString();
        Assertions.assertNotNull(connection);
    }
}
