import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class dataRetriever {
    public void retrieveData() throws IOException, ParseException {
        String urlString =  URLGenerator("2022-12-06", "2022-12-08");
        URL url = new URL(urlString);
        URLConnection connect = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONParser parser = new JSONParser();
        JSONObject responseJSONObject = (JSONObject) parser.parse(response.toString());
        JSONArray recordsJSONArray = (JSONArray) responseJSONObject.get("records");
        for (int i = 0; i < recordsJSONArray.size(); i++) {
            JSONObject record = (JSONObject) recordsJSONArray.get(i);
            System.out.println(record.get("HourDK"));
            System.out.println(record.get("SpotPriceDKK"));
        }
        System.out.println("Slut");
    }
    public String URLGenerator(String includedStartDate, String notIncludedEndDate) {
        String URLString = "https://api.energidataservice.dk/dataset/Elspotprices?" + "start=" + includedStartDate + "&end=" + notIncludedEndDate + "&sort=HourDK&columns=HourDK,PriceArea,SpotPriceDKK&filter={\"PriceArea\":[\"DK2\"]}";
        return URLString;
    }
}
