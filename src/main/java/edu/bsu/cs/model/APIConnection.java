package edu.bsu.cs.model;

import edu.bsu.cs.utility.ErrorPrinter;
import edu.bsu.cs.utility.JSONDataGetter;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

@SuppressWarnings("ALL")
public class APIConnection extends JSONDataGetter {
    @Deprecated
    static Properties properties = new Properties();
    static ErrorReport errorReport = new ErrorReport();
    static ErrorPrinter errorPrinter = new ErrorPrinter();


    static String APIKey;
    static URL APIUrl;
    static String connectionStatusMessage;
    static int responseCode;
    static HttpsURLConnection APIconnection;
    static String APIUsageMessage;

    public static HttpsURLConnection encodedUrlString() throws IOException {
        APIKey = getAPIKey();
        APIUrl = new URL("https://api.exchangeratesapi.io/v1/latest?access_key=" + APIKey + "&format=1");

        APIconnection = (HttpsURLConnection) APIUrl.openConnection();

        if (APIconnection.getResponseCode() == 429) {
            check429Error(APIconnection);
            throw new IOException("API rate limit exceeded");
        }

        return APIconnection;
    }

    public static String getAPIKey() {
        try (InputStream inputStream = APIConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
            return properties.getProperty("apiKey");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkConnectionError(URL url) throws IOException {
        connectionStatusMessage = errorReport.checkConnectionStatus(url);
        errorPrinter.printConnectionMessageError(connectionStatusMessage);
    }

    public static void check429Error(HttpsURLConnection connection) throws IOException {
        responseCode = connection.getResponseCode();
        APIUsageMessage = errorReport.check429Status(responseCode);
        errorPrinter.print429Error(APIUsageMessage);
    }

}