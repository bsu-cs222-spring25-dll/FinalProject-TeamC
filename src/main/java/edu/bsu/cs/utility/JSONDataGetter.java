package edu.bsu.cs.utility;

import java.io.IOException;
import java.net.URLConnection;

public class JSONDataGetter {
    public String dataGetter(URLConnection connection) throws IOException {
        return new String(connection.getInputStream().readAllBytes());
    }
}
