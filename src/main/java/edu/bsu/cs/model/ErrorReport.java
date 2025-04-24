package edu.bsu.cs.model;

import java.net.URL;

public class ErrorReport {

    public String checkConnectionStatus(URL url) {
        try {
            url.openConnection().connect();
        } catch (Exception NetworkError) {
            return "There was a network error; could not connect to the internet";
        }
        return "";
    }

    public String check429Status(int responseCode) {
        return responseCode == 429 ? "API rate limit exceeded" : "";
    }
}
