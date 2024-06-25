package com.example.ea3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class Kcal extends AppCompatActivity {

    private EditText newKcalRecord;
    private Button btnKcal, btnShowKcalRecord;
    private static String url = "http://10.0.2.2/myphp/addKcalRecord.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kcal);

        newKcalRecord = findViewById(R.id.newKcalRecord);
        btnKcal = findViewById(R.id.btnKcal);
        btnShowKcalRecord = findViewById(R.id.btnShowKcalRecord);

        btnKcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kcalValue = newKcalRecord.getText().toString().trim();


                new MyAsyncTask().execute(url);

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

    public String executeHttpPost (String url){
        String result = "";

        //HttpClient acts like a Browser (without the UI)
        HttpClient client = new DefaultHttpClient();

        // Create object to represent a POST request
        HttpPost request = new HttpPost(url);

        List<NameValuePair> nameValuePairs =
                new ArrayList<NameValuePair>(2);

        // $_POST[] values to PHP
        nameValuePairs.add(new BasicNameValuePair
                ("newKcalRecord",newKcalRecord.getText().toString()));

        // This will store the response from the server
        HttpResponse response;

        try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Actually call the server
            response = client.execute(request);

            //Extract text message from server
            result = EntityUtils.toString(response.getEntity());
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
            Toast.makeText(getApplicationContext(),
                    "Connecting to server...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... url) {
            return executeHttpPost(url[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}