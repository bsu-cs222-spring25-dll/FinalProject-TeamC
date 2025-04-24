package edu.bsu.cs;

import edu.bsu.cs.utility.JSONparser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class JSONparserTest {
    @Test
    public void parserTest() throws IOException {
        JSONparser parser = new JSONparser();
        String base = "EUR";
        String target = "USD";
        InputStream JSONdata = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.json");
        Double result = parser.parse(target,JSONdata);
        Assertions.assertEquals(1.047916,result);
    }
}
