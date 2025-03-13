import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class APIConnection extends JSONDataGetter{
    @Deprecated
    static String APIKey = "91c58f29bd36a7471e286d9ef212bc6a";
    static URL APIUrl;
    public static URLConnection encodedUrlString() throws IOException {

        String encodedUrlString = "http://api.exchangeratesapi.io/v1/latest?access_key=" + URLEncoder.encode(APIKey) + "&format=1";

        APIUrl = new URL(encodedUrlString);
        URLConnection connection = APIUrl.openConnection();
        connection.connect();

        return connection;
    }

    public static String getUrlAsStr(String base){
        String encodedUrlString = "http://api.exchangeratesapi.io/v1/latest?access_key=" + URLEncoder.encode(APIKey) + URLEncoder.encode(base) + "&format=1";
        return encodedUrlString;
    }



}