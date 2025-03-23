import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;

public class JSONparser {
    public double parse(String target, InputStream JSONdata) throws IOException {
        JSONArray value = JsonPath.read(JSONdata, "$.." + target);
        String conversionString = value.getFirst().toString();
        return Double.parseDouble(conversionString);
    }
}
