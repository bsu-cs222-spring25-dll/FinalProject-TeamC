import java.io.IOException;
import java.net.URLConnection;

public class DisplayJSONData {
    public String dataGetter(URLConnection apiConnection) throws IOException {
        return new String(apiConnection.getInputStream().readAllBytes());
    }
}
