package com.example.humancomputerinteraction;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
public class Weather extends AppCompatActivity {

    private EditText cityEditText;
    private TextView displayedCityName;
    private TextView temperatureTextView;
    private TextView minTemperatureTextView;
    private TextView maxTemperatureTextView;
    private TextView humidityTextView;
    private Button temperatureButton;
    private String preset_city = "Athens";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityEditText = findViewById(R.id.city_name);
        temperatureTextView = findViewById(R.id.temp);
        minTemperatureTextView = findViewById(R.id.min_temp);
        maxTemperatureTextView = findViewById(R.id.max_temp);
        humidityTextView = findViewById(R.id.humidity);
        temperatureButton = findViewById(R.id.temperature_button);

        // Initial temperature "update"
        findTemperature(preset_city);

        Button temperatureButton = findViewById(R.id.temperature_button);
        temperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = cityEditText.getText().toString().trim();
                if (!city.isEmpty()) {
                    findTemperature(city);
                } else {
                    Log.e("MainActivity", "City name is empty");
                }
            }
        });
    }

    private void findTemperature(String city) {

        if (city.isEmpty() || city.equals(" ")){city = "athens";}

        displayedCityName = findViewById(R.id.chosen_city);

        //Set first letter to uppercase
        String cityOnDisplay = city.substring(0, 1).toUpperCase() + city.substring(1);
        displayedCityName.setText(cityOnDisplay);

        new WeatherAsyncTask().execute(city);
    }
    private class WeatherAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            String city = params[0];
            JSONObject jsonResponse = null;

            try {
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                        "&appid=2aa1679ccf6c74ccbc7270f2035404c0";


                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                jsonResponse = new JSONObject(response.toString());
            } catch (IOException | org.json.JSONException e) {
                Log.e("WeatherAsyncTask", "Error fetching data: " + e.getMessage());
            }

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(JSONObject jsonResponse) {
            super.onPostExecute(jsonResponse);

            if (jsonResponse != null) {
                try {
                    JSONObject mainData = jsonResponse.getJSONObject("main");
                    double temperatureKelvin = mainData.getDouble("temp");
                    double tempMinKelvin = mainData.getDouble("temp_min");
                    double tempMaxKelvin = mainData.getDouble("temp_max");
                    int humidity = mainData.getInt("humidity");

                    //temperature units conversion
                    double temperatureCelsius = temperatureKelvin - 273.15;
                    double tempMinCelsius = tempMinKelvin - 273.15;
                    double tempMaxCelsius = tempMaxKelvin - 273.15;

                    DecimalFormat df = new DecimalFormat("#0.0");
                    temperatureCelsius = Double.parseDouble(df.format(temperatureCelsius));
                    tempMinCelsius = Double.parseDouble(df.format(tempMinCelsius));
                    tempMaxCelsius = Double.parseDouble(df.format(tempMaxCelsius));

                    int temperatureCelsiusInt =  (int) temperatureCelsius;
                    int tempMinCelsiusInt = (int) tempMinCelsius;
                    int tempMaxCelsiusInt = (int) tempMaxCelsius;

                    temperatureTextView.setText("Temperature: " + temperatureCelsiusInt + "°C");
                    maxTemperatureTextView.setText("Maximum Temperature: " + tempMaxCelsiusInt + "°C");
                    minTemperatureTextView.setText("Minimum Temperature: " + tempMinCelsiusInt + "°C");
                    humidityTextView.setText("Humidity: " + humidity + "%");
                } catch (org.json.JSONException e) {
                    Log.e("WeatherAsyncTask", "Error parsing JSON response: " + e.getMessage());
                }
            } else {
                Log.e("WeatherAsyncTask", "Empty JSON response");
            }
        }
    }
}