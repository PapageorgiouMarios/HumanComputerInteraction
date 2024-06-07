package com.example.humancomputerinteraction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class Weather extends AppCompatActivity {

    private EditText cityEditText;
    private TextView temperatureTextView;
    private TextView minTemperatureTextView;
    private TextView maxTemperatureTextView;
    private TextView humidityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Initialize UI components
        cityEditText = findViewById(R.id.city_name);
        temperatureTextView = findViewById(R.id.temp);
        minTemperatureTextView = findViewById(R.id.min_temp);
        maxTemperatureTextView = findViewById(R.id.max_temp);
        humidityTextView = findViewById(R.id.humidity);

        // Find Temperature Button
        Button temperatureButton = findViewById(R.id.temperature_button);
        temperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findTemperature();
            }
        });
    }

    private void findTemperature() {
        String city = cityEditText.getText().toString().trim();
        Log.i("Weather", "findTemperature()");
        if (!city.isEmpty()) {
            //new WeatherAsyncTask().execute(city);
           func(city);
        } else {

            Log.e("Weather", "City name is empty");
        }
    }


        protected void func(String... params) {
            String city = params[0];
            JSONObject jsonResponse = null;

            Log.i("Weather", "70");

            try {
                // Construct API URL based on city name
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                        "&appid=2aa1679ccf6c74ccbc7270f2035404c0";

                //Open connection to the API URL
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                Log.i("Weather", "api");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response
                jsonResponse = new JSONObject(response.toString());
            } catch (IOException | org.json.JSONException e) {
                Log.e("Weather", "Error fetching data: " + e.getMessage());
            }

            Log.i("Weather", "postExecution");

            if (jsonResponse != null) {
                try {
                    JSONObject mainData = jsonResponse.getJSONObject("main");
                    double temperatureKelvin = mainData.getDouble("temp");
                    double tempMinKelvin = mainData.getDouble("temp_min");
                    double tempMaxKelvin = mainData.getDouble("temp_max");
                    int humidity = mainData.getInt("humidity");

                    String tmK = String.valueOf(temperatureKelvin);
                    Log.i("Weather",tmK);


                    // Convert temperature units
/*                    double temperatureCelsius = temperatureKelvin - 273.15;
                    double tempMinCelsius = tempMinKelvin - 273.15;
                    double tempMaxCelsius = tempMaxKelvin - 273.15;

                    // Format temperature values
                    DecimalFormat df = new DecimalFormat("#0.0");
                    temperatureCelsius = Double.parseDouble(df.format(temperatureCelsius));
                    tempMinCelsius = Double.parseDouble(df.format(tempMinCelsius));
                    tempMaxCelsius = Double.parseDouble(df.format(tempMaxCelsius));*/

                } catch (org.json.JSONException e) {
                    Log.e("Weather", "Error parsing JSON response: " + e.getMessage());
                }
            } else {
                Log.e("Weather", "Empty JSON response");
            }
        }

/*    private class WeatherAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            String city = params[0];
            JSONObject jsonResponse = null;

            Log.i("Weather", "70");

            try {
                // Construct API URL based on city name
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                        "&appid=2aa1679ccf6c74ccbc7270f2035404c0";

                //Open connection to the API URL
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                Log.i("Weather", "api");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response
                jsonResponse = new JSONObject(response.toString());
            } catch (IOException | org.json.JSONException e) {
                Log.e("Weather", "Error fetching data: " + e.getMessage());
            }

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(JSONObject jsonResponse) {
            super.onPostExecute(jsonResponse);

            Log.i("Weather", "postExecution");

            if (jsonResponse != null) {
                try {
                    JSONObject mainData = jsonResponse.getJSONObject("main");
                    double temperatureKelvin = mainData.getDouble("temp");
                    double tempMinKelvin = mainData.getDouble("temp_min");
                    double tempMaxKelvin = mainData.getDouble("temp_max");
                    int humidity = mainData.getInt("humidity");

                    String tmK = String.valueOf(temperatureKelvin);
                    Log.e("Weather",tmK);


                    // Convert temperature units
                    double temperatureCelsius = temperatureKelvin - 273.15;
                    double tempMinCelsius = tempMinKelvin - 273.15;
                    double tempMaxCelsius = tempMaxKelvin - 273.15;

                    // Format temperature values
                    DecimalFormat df = new DecimalFormat("#0.0");
                    temperatureCelsius = Double.parseDouble(df.format(temperatureCelsius));
                    tempMinCelsius = Double.parseDouble(df.format(tempMinCelsius));
                    tempMaxCelsius = Double.parseDouble(df.format(tempMaxCelsius));

                    *//*String tempC = String.valueOf(temperatureCelsius);
                    String tempm = String.valueOf(tempMinCelsius);
                    String tempM = String.valueOf(tempMaxCelsius);
                    String hmdt = String.valueOf(humidity);*//*

                    *//*Log.e("Weather",tempC);
                    Log.e("Weather",tempm);
                    Log.e("Weather",tempM);
                    Log.e("Weather",hmdt);*//*

                    // Update UI with temperature data
*//*                    temperatureTextView.setText("Temperature: " + temperatureCelsius + "°C");
                    minTemperatureTextView.setText("Minimum Temperature: " + tempMinCelsius + "°C");
                    maxTemperatureTextView.setText("Maximum Temperature: " + tempMaxCelsius + "°C");
                    humidityTextView.setText("Humidity: " + humidity + "%");*//*
                } catch (org.json.JSONException e) {
                    Log.e("Weather", "Error parsing JSON response: " + e.getMessage());
                }
            } else {
                Log.e("Weather", "Empty JSON response");
            }
        }
    }*/
}
