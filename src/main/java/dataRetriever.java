import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class dataRetriever {

    public void retrieveData() throws IOException, ParseException {
        // Generate the URL for the energy data service
        String urlString = generateEnergyDataServiceURL("2022-12-06", "2022-12-08", "HourDK", "HourDK,SpotPriceDKK", "{\"PriceArea\":[\"DK2\"]}");

        // Open a connection to the URL and read the response
        URL url = new URL(urlString);
        InputStream inputStream = url.openConnection().getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // Concatenate the lines from the response into a single string
        String response = reader.lines().collect(Collectors.joining());

        // Parse the response into a JSONObject and get the "records" JSONArray from it
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        JSONArray records = (JSONArray) json.get("records");

        Map<String, Double> data = new HashMap<>();

        for (Object JSONObject : records) {
            JSONObject record = (JSONObject) JSONObject;

            // Get the HourDK and SpotPriceDKK values from each object in records
            String hourDK = (String) record.get("HourDK");
            double spotPriceDKK = (double) record.get("SpotPriceDKK");

            // Store the values in the HashMap
            data.put(hourDK, spotPriceDKK / 1000);
        }
        System.out.println(data);
    }

    public String generateEnergyDataServiceURL(String startDate, String endDate, String sortBy, String columns, String filter) {
        // Create a new StringJoiner to build the URL string
        StringJoiner urlJoiner = new StringJoiner("&", "https://api.energidataservice.dk/dataset/Elspotprices?", "");

        // Add each query parameter to the URL string
        urlJoiner.add("start=" + startDate);
        urlJoiner.add("end=" + endDate);
        urlJoiner.add("sort=" + sortBy);
        urlJoiner.add("columns=" + columns);
        urlJoiner.add("filter=" + filter);

        // Return the generated URL string
        System.out.println(urlJoiner.toString());
        return urlJoiner.toString();
    }
}
