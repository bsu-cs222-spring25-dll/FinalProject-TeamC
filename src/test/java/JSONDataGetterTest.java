import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLConnection;

public class JSONDataGetterTest {
    @Test
    public void dataGetterTest() throws IOException {
        JSONDataGetter dataGetter = new JSONDataGetter();
        URLConnection connection = APIConnection.encodedUrlString();
        String testResult = dataGetter.dataGetter(connection);
        Assertions.assertNotNull(testResult);
    }
}
