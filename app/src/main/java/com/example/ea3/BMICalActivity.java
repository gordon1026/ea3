package com.example.ea3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BMICalActivity extends AppCompatActivity {


    private EditText etHeight, etWeight;
    private TextView bmiValue, bmiCategory;
    private Button enterButton;
    private int userId;
    private float bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmical);

        etHeight = findViewById(R.id.etKG);
        etWeight = findViewById(R.id.etCM);
        bmiValue = findViewById(R.id.additionalText);
        bmiCategory = findViewById(R.id.BMItextView);
        enterButton = findViewById(R.id.enterButton);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        String heightStr = etHeight.getText().toString();
        String weightStr = etWeight.getText().toString();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_SHORT).show();
            return;
        }

        float height = Float.parseFloat(heightStr) / 100; // Convert cm to meters
        float weight = Float.parseFloat(weightStr);

        bmi = weight / (height * height);
        bmiValue.setText(String.format("%.1f", bmi));

        if (bmi < 18.5) {
            bmiCategory.setText("Underweight");
        } else if (bmi >= 18.5 && bmi < 24) {
            bmiCategory.setText("Healthy Weight");
        } else if (bmi >= 24 && bmi <= 27) {
            bmiCategory.setText("Overweight");
        } else {
            bmiCategory.setText("Obesity");
        }
    }
    public String executeHttpPost(String url) {
        String result = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        // Get the current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());

        SharedPreferences sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1); // Default value is -1 if userId is not found

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("userId", String.valueOf(userId)));
        nameValuePairs.add(new BasicNameValuePair("bmi", String.valueOf(bmi)));
        HttpResponse response;
        try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Actually call the server
            response = client.execute(request);

            // Extract text message from server
            result = EntityUtils.toString(response.getEntity());
            Log.v("myLog", "Response from server: " + result);
        } catch (Exception e) {
            result = "[ERROR] " + e.toString();
            Log.v("myLog", "result: " + result);
        }
        return result;
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Connecting to server...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... url) {
            return executeHttpPost(url[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}

