package com.example.ea3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowWeather extends AppCompatActivity {

    private TextView tvPlace, tvTemperature, tvTime, tvTodayWeather, tvFutureWeather;
    private TextView tvDay0Week, tvDay0Temperature, tvDay0RSRpercentage, tvDay0Weather;
    private TextView tvDay1Week, tvDay1Temperature, tvDay1RSRpercentage, tvDay1Weather;
    private TextView tvDay2Week, tvDay2Temperature, tvDay2RSRpercentage, tvDay2Weather;
    private TextView tvDay3Week, tvDay3Temperature, tvDay3RSRpercentage, tvDay3Weather;
    private TextView tvDay4Week, tvDay4Temperature, tvDay4RSRpercentage, tvDay4Weather;
    private ImageView ivWeatherIcon, ivDay0WeatherIcon,ivDay1WeatherIcon, ivDay2WeatherIcon, ivDay3WeatherIcon,ivDay4WeatherIcon;
    private ImageView btnBackTo;


    private String baseUrl = "https://data.weather.gov.hk/weatherAPI/opendata/weather.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        tvPlace = (TextView) findViewById(R.id.tvPlace);
        tvTemperature = (TextView)findViewById(R.id.tvTemperature);
        ivWeatherIcon = (ImageView) findViewById(R.id.ivWeatherIcon);
        btnBackTo = findViewById(R.id.BackTo);
        tvTodayWeather = (TextView)findViewById(R.id.tvTodayWeather);
        tvFutureWeather = (TextView)findViewById(R.id.tvFutureWeather);

        //week
        tvDay0Week= (TextView)findViewById(R.id.tvDay0Week);
        tvDay0Temperature = (TextView)findViewById(R.id.tvDay0Temperature);
        tvDay0RSRpercentage = (TextView)findViewById(R.id.tvDay0RSRpercentage);
        tvDay0Weather = (TextView)findViewById(R.id.tvDay0Weather);
        ivDay0WeatherIcon = (ImageView)findViewById(R.id.ivDay0WeatherIcon);

        tvDay1Week= (TextView)findViewById(R.id.tvDay1Week);
        tvDay1Temperature = (TextView)findViewById(R.id.tvDay1Temperature);
        tvDay1RSRpercentage = (TextView)findViewById(R.id.tvDay1RSRpercentage);
        tvDay1Weather = (TextView)findViewById(R.id.tvDay1Weather);
        ivDay1WeatherIcon = (ImageView)findViewById(R.id.ivDay1WeatherIcon);

        tvDay2Week= (TextView)findViewById(R.id.tvDay2Week);
        tvDay2Temperature = (TextView)findViewById(R.id.tvDay2Temperature);
        tvDay2RSRpercentage = (TextView)findViewById(R.id.tvDay2RSRpercentage);
        tvDay2Weather = (TextView)findViewById(R.id.tvDay2Weather);
        ivDay2WeatherIcon = (ImageView)findViewById(R.id.ivDay2WeatherIcon);

        tvDay3Week= (TextView)findViewById(R.id.tvDay3Week);
        tvDay3Temperature = (TextView)findViewById(R.id.tvDay3Temperature);
        tvDay3RSRpercentage = (TextView)findViewById(R.id.tvDay3RSRpercentage);
        tvDay3Weather = (TextView)findViewById(R.id.tvDay3Weather);
        ivDay3WeatherIcon = (ImageView)findViewById(R.id.ivDay3WeatherIcon);

        tvDay4Week= (TextView)findViewById(R.id.tvDay4Week);
        tvDay4Temperature = (TextView)findViewById(R.id.tvDay4Temperature);
        tvDay4RSRpercentage = (TextView)findViewById(R.id.tvDay4RSRpercentage);
        tvDay4Weather = (TextView)findViewById(R.id.tvDay4Weather);
        ivDay4WeatherIcon = (ImageView)findViewById(R.id.ivDay4WeatherIcon);





        // 定義不同的remainUrls
        String[] remainUrls = {
                "dataType=rhrread&lang=tc",
                "dataType=flw&lang=tc",
                "dataType=fnd&lang=tc"
        };

        // 遍歷每個URL並執行一個新的AsyncTask
        for (String remainUrl : remainUrls) {
            String fullUrl = baseUrl + remainUrl;
            new MyAsyncTask(remainUrl).execute(fullUrl);
        }

        btnBackTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    public void method1(String result) throws JSONException {
        JSONObject weatherJson = new JSONObject(result);
        String iconID = weatherJson.getJSONArray("icon").getString(0); // 獲取第一個圖標ID

        JSONObject temperatureJson = weatherJson.getJSONObject("temperature");
        JSONArray temperatureData = temperatureJson.getJSONArray("data");

        String weatherIconName = "weather" + iconID;
        int weatherIconResourceId = getResources().getIdentifier(weatherIconName, "drawable", getPackageName());
        ivWeatherIcon.setImageResource(weatherIconResourceId);

        for (int i = 0; i < temperatureData.length(); i++) {
            JSONObject temp = temperatureData.getJSONObject(i);
            if (temp.getString("place").equals("屯門") || temp.getString("place").equals("Tuen Mun")) {
                String place = temp.getString("place");
                String temperature = temp.getString("value");
                String unit = temp.getString("unit");

                tvPlace.setText(place);
                tvTemperature.setText(temperature + "°" + unit);
                break;
            }
        }
    }

    public void method2(String result)throws JSONException {

        JSONObject weatherJsonData = new JSONObject(result);
        String todayWeather = weatherJsonData.getString("forecastDesc");
        String futureWeather= weatherJsonData.getString("outlook");
        tvTodayWeather.setText(todayWeather);
        tvFutureWeather.setText(futureWeather);

    }

    public void method3(String result)throws JSONException {


        JSONObject weekDataJson = new JSONObject(result);
        // String iconID = weekDataJson.getJSONArray("icon").getString(0);
        JSONArray weekData = weekDataJson.getJSONArray("weatherForecast");

        for (int i = 0; i < 5; i++) {
            JSONObject dayData = weekData.getJSONObject(i);

            String iconID = dayData.getString("ForecastIcon");
            String week = dayData.getString("week");
            String forecastWeather = dayData.getString("forecastWeather");

            JSONObject forecastMaxTemp = dayData.getJSONObject("forecastMaxtemp");
            int maxTemp = forecastMaxTemp.getInt("value");
            String maxTempUnit = forecastMaxTemp.getString("unit");

            JSONObject forecastMinTemp = dayData.getJSONObject("forecastMintemp");
            int minTemp = forecastMinTemp.getInt("value");
            String minTempUnit = forecastMinTemp.getString("unit");

            String PSR = dayData.getString("PSR");

            if(i==0){
                tvDay0Week.setText(week);
                tvDay0Temperature.setText(minTemp + "°" + maxTempUnit + " ~ " + maxTemp + "°" + minTempUnit);
                tvDay0RSRpercentage.setText("%"+PSR);
                tvDay0Weather.setText(forecastWeather);

                String weatherIconName = "weather" + iconID;
                int weatherIconResourceId = getResources().getIdentifier(weatherIconName, "drawable", getPackageName());
                ivDay0WeatherIcon.setImageResource(weatherIconResourceId);
            }else if(i==1){
                tvDay1Week.setText(week);
                tvDay1Temperature.setText(minTemp + "°" + maxTempUnit + " ~ " + maxTemp + "°" + minTempUnit);
                tvDay1RSRpercentage.setText("%"+PSR);
                tvDay1Weather.setText(forecastWeather);

                String weatherIconName = "weather" + iconID;
                int weatherIconResourceId = getResources().getIdentifier(weatherIconName, "drawable", getPackageName());
                ivDay1WeatherIcon.setImageResource(weatherIconResourceId);
            }else if(i==2){
                tvDay2Week.setText(week);
                tvDay2Temperature.setText(minTemp + "°" + maxTempUnit + " ~ " + maxTemp + "°" + minTempUnit);
                tvDay2RSRpercentage.setText("%"+PSR);
                tvDay2Weather.setText(forecastWeather);

                String weatherIconName = "weather" + iconID;
                int weatherIconResourceId = getResources().getIdentifier(weatherIconName, "drawable", getPackageName());
                ivDay2WeatherIcon.setImageResource(weatherIconResourceId);

            }else if(i==3){
                tvDay3Week.setText(week);
                tvDay3Temperature.setText(minTemp + "°" + maxTempUnit + " ~ " + maxTemp + "°" + minTempUnit);
                tvDay3RSRpercentage.setText("%"+PSR);
                tvDay3Weather.setText(forecastWeather);

                String weatherIconName = "weather" + iconID;
                int weatherIconResourceId = getResources().getIdentifier(weatherIconName, "drawable", getPackageName());
                ivDay3WeatherIcon.setImageResource(weatherIconResourceId);

            }else if(i==4) {
                tvDay4Week.setText(week);
                tvDay4Temperature.setText(minTemp + "°" + maxTempUnit + " ~ " + maxTemp + "°" + minTempUnit);
                tvDay4RSRpercentage.setText("%"+PSR);
                tvDay4Weather.setText(forecastWeather);

                String weatherIconName = "weather" + iconID;
                int weatherIconResourceId = getResources().getIdentifier(weatherIconName, "drawable", getPackageName());
                ivDay4WeatherIcon.setImageResource(weatherIconResourceId);


            }
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        private String remainUrl;

        public MyAsyncTask(String remainUrl) {
            this.remainUrl = remainUrl;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Fetching Data", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... urls) {
            return executeHttpGet(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                if (remainUrl.contains("rhrread")) {
                    method1(result);
                }else if (remainUrl.contains("flw")) {
                    method2(result);
                }else if (remainUrl.contains("fnd")) {
                    method3(result);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error parsing JSON for: " + remainUrl, Toast.LENGTH_LONG).show();
            }
        }

        public String executeHttpGet(String url) {
            String result = "";
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response;

            try {
                response = client.execute(request);
                result = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {
                result = "[ERROR] " + e.toString();
                Log.v("myLog", "result: " + result);
            }
            return result;
        }
    }
}