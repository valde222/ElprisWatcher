import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        dataRetriever data = new dataRetriever();
        data.retrieveData();
    }
}
