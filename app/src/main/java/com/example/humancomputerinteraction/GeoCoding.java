package com.example.weatherimporttestapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GeoCoding {

    private String city;

    public GeoCoding(String city) {
        this.city = city;
    }

    public double getLat() {
        double[] latLon = getLatLon();
        if (latLon != null) {
            return latLon[0];
        } else {
            System.out.println("Failed to retrieve latitude.");
            return 0.0; // Default latitude value if retrieval fails
        }
    }

    public double getLon() {
        double[] latLon = getLatLon();
        if (latLon != null) {
            return latLon[1];
        } else {
            System.out.println("Failed to retrieve longitude.");
            return 0.0; // Default longitude value if retrieval fails
        }
    }

    private double[] getLatLon() {
        try {
            // Construct the API URL
            String apiUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=2aa1679ccf6c74ccbc7270f2035404c0";

            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            // If response code is not OK, return null
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Error retrieving latitude and longitude: Server returned HTTP response code: " + responseCode);
                return null;
            }

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response
            JSONArray jsonArray = new JSONArray(response.toString());
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                double lat = jsonObject.getDouble("lat");
                double lon = jsonObject.getDouble("lon");
                return new double[]{lat, lon};
            } else {
                System.out.println("No coordinates found for the city: " + city);
                return null;
            }
        } catch (IOException | JSONException e) {
            System.out.println("Failed to retrieve latitude and longitude: " + e.getMessage());
            return null;
        }
    }
}
