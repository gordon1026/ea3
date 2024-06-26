package com.example.ea3;

import android.database.Cursor;
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

public class SignUpActivity extends AppCompatActivity {

    private EditText etUserName, etRePassword, etPassword;
    private Button btnSignUp;
    private DBHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUserName = findViewById(R.id.etUserName);
        etRePassword = findViewById(R.id.etRePassword);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        dbHelper = new DBHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString().trim();
                String repassword = etRePassword.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || repassword.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (password.equals(repassword)) {
                    if (dbHelper.addUser(username, password)) {
                        Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();

                        // Fetch the newly created user's ID
                        Cursor cursor = dbHelper.getUser(username);
                        if (cursor.moveToFirst()) {
                            userId = cursor.getInt(cursor.getColumnIndex("id"));
                            cursor.close();

                            // Insert initial data into MySQL database via PHP
                            new MyAsyncTask().execute("http://10.0.2.2:8080/1/create.php", String.valueOf(userId));
                        } else {
                            cursor.close();
                        }

                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Error Creating Account", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String executeHttpPost(String url, String userId) {
        String result = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("userId", userId));
        HttpResponse response;
        try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.v("myLog", "Sending request to: " + url + " with data: " + userId);

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
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String userId = params[1];
            return executeHttpPost(url, userId);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);}
    }
}