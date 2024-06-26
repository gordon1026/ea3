package com.example.ea3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShowKcalRecord extends AppCompatActivity {

    private ListView lv;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_kcal_record);

        lv = findViewById(R.id.list);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1); // Default value is -1 if userId is not found

        if (userId == -1) {
            Toast.makeText(this, "User ID not found. Please sign in again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        String url = "http://10.0.2.2:8080/1/record_json.php?userId=" + userId;
        new JsonTask().execute(url);
    }

    private class JsonTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(ShowKcalRecord.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                    Log.d("Response: ", "> " + line); // Log the response
                }

                return buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }

            ArrayList<KcalRecord> arrayList = convertJsonToArrayList(result);
            KcalRecord[] kcalData = arrayList.toArray(new KcalRecord[arrayList.size()]);
            KcalAdapter adapter = new KcalAdapter(ShowKcalRecord.this, R.layout.list_item_kcal, kcalData);
            lv.setAdapter(adapter);
        }

        private ArrayList<KcalRecord> convertJsonToArrayList(String jsonStr) {
            ArrayList<KcalRecord> arrayList = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(jsonStr);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String date = jsonObj.getString("date");
                    int kcal = jsonObj.getInt("kcal");

                    arrayList.add(new KcalRecord(date, kcal));
                }
            } catch (JSONException e) {
                Log.v("myLog", e.toString());
            }

            return arrayList;
        }
    }
}