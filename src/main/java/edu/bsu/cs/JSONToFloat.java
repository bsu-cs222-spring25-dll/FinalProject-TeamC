package edu.bsu.cs;

import net.minidev.json.JSONArray;

public class JSONToFloat {
    public Float jsonArrayToFloat(JSONArray array) {
        String arrayString = String.valueOf(array);
        String stringNoBrackets = arrayString.substring(1, arrayString.length()-1);
        return Float.parseFloat(stringNoBrackets);
    }
}
