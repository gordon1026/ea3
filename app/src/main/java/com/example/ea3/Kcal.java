package com.example.ea3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Kcal extends AppCompatActivity {

    private EditText newKcalRecord;
    private Button btnKcal, btnShowKcalRecord;
    private ImageView btnBackTo;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kcal);

        newKcalRecord = findViewById(R.id.newKcalRecord);
        btnKcal = findViewById(R.id.btnKcal);
        btnShowKcalRecord = findViewById(R.id.btnShowKcalRecord);
        btnBackTo = findViewById(R.id.BackTo);

        // Retrieve userId from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1); // Default value is -1 if userId is not found

        if (userId == -1) {
            Toast.makeText(this, "User ID not found. Please sign in again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        btnBackTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnKcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask().execute("http://10.0.2.2:8080/1/addKcalRecord.php");
            }
        });

        btnShowKcalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kcal.this, ShowKcalRecord.class);
                startActivity(intent);
            }
        });
    }

    public String executeHttpPost(String url) {
        String result = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        // Get the current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("userId", String.valueOf(userId)));
        nameValuePairs.add(new BasicNameValuePair("newKcalRecord", newKcalRecord.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("date", currentDateTime));
        HttpResponse response;
        try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.v("myLog", "Sending request to: " + url + " with userId: " + userId + ", data: " + newKcalRecord.getText().toString() + ", date: " + currentDateTime);

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