package edu.bsu.cs;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SuppressWarnings("ALL")
public class APIConnection extends JSONDataGetter{
    @Deprecated
    static String APIKey = "92a4a7a22301e1794c33f6f39f3db1a8";
    static URL APIUrl;

    public static URLConnection encodedUrlString() throws IOException {
        ErrorReport error = new ErrorReport();

        String encodedUrlString = "http://api.exchangeratesapi.io/v1/latest?access_key=" + URLEncoder.encode(APIKey) + "&format=1";
        APIUrl = new URL(encodedUrlString);
        String checkConnection = error.chechkConnectionStatus(APIUrl);
        if (checkConnection.equals("There was a network error; could not connect to the internet")) {
            System.out.println(checkConnection);
            return null;
        }else{
            URLConnection connection = APIUrl.openConnection();
            connection.connect();
            return connection;
        }
    }

    public static String getUrlAsStr(String base){
        String encodedUrlString = "http://api.exchangeratesapi.io/v1/latest?access_key=" + URLEncoder.encode(APIKey) + URLEncoder.encode(base) + "&format=1";
        return encodedUrlString;
    }
}