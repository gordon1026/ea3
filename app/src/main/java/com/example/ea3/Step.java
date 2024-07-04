package com.example.ea3;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Step extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor stepSensor;
    private int totalSteps = 0;
    private int previousTotalSteps = 0;
    private int stepGoal = 10000;
    private Button sava;
    private TextView steps;
    private EditText stepGoalInput;
    private Button setGoalButton;
    private double newKcalRecord;
    private int userId;
    private ImageView btnBackTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step);

        steps = findViewById(R.id.tv_step_count);
        stepGoalInput = findViewById(R.id.et_step_goal);
        setGoalButton = findViewById(R.id.btn_set_goal);
        sava = findViewById(R.id.btn_sava);
        btnBackTo = findViewById(R.id.BackTo);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (mSensorManager != null) {
            stepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }

        loadData();
        resetSteps();

        btnBackTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        steps.setText(String.valueOf(0));
        sava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask().execute("http://10.0.2.2:8080/1/addKcalRecord.php");

            }
        });
        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalInput = stepGoalInput.getText().toString();
                if (!goalInput.isEmpty()) {
                    stepGoal = Integer.parseInt(goalInput);
                    saveData();
                    Toast.makeText(Step.this, "Step goal updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Step.this, "Pleasecenter a valid step goal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (stepSensor == null) {
            Toast.makeText(this, "This device has no step sensor", Toast.LENGTH_SHORT).show();
        } else {
            mSensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            totalSteps = (int) event.values[0];
            int currentSteps = totalSteps - previousTotalSteps;
            steps.setText(String.valueOf(currentSteps));

            // Calculate calories burned
            newKcalRecord = calculateCalories(currentSteps);
            TextView caloriesTextView = findViewById(R.id.tv_show_calories);
            caloriesTextView.setText(String.format("%.2f", newKcalRecord));


            Log.d("StepCounter", "Total Steps: " + totalSteps + ", Previous Total Steps: " + previousTotalSteps + ", Current Steps: " + currentSteps + ", Calories Burned: " + newKcalRecord);

            // Check if step goal is reached
            if (currentSteps >= stepGoal) {
                showCongratulatoryMessage();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used, but required to implement SensorEventListener
    }

    private void resetSteps() {
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Step.this, "Long press to reset steps", Toast.LENGTH_SHORT).show();
            }
        });

        steps.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                previousTotalSteps = totalSteps;
                steps.setText("0");
                saveData();
                return true;
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Key1", previousTotalSteps);
        editor.putInt("stepGoal", stepGoal); // 保存步数目标
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        previousTotalSteps = sharedPreferences.getInt("Key1", 0);
        stepGoal = sharedPreferences.getInt("stepGoal", 10000); // 读取步数目标
    }

    private double calculateCalories(int steps) {
        double caloriesPerStep = 0.05;
        return steps * caloriesPerStep;
    }

    private void showCongratulatoryMessage() {
        Toast toast = Toast.makeText(getApplicationContext(), "恭喜你达到了目标步数！", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
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
        nameValuePairs.add(new BasicNameValuePair("newKcalRecord", String.valueOf(newKcalRecord)));
        nameValuePairs.add(new BasicNameValuePair("date", currentDateTime));
        HttpResponse response;
        try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.v("myLog", "Sending request to: " + url + " with userId: " + userId + ", data: " + newKcalRecord + ", date: " + currentDateTime);

            // Actually call the server
            response = client.execute(request);

            // Extract text message from server
            result = EntityUtils.toString(response.getEntity());
            Log.v("myLog", "Response from server: " + result);
        } catch (Exception e) {
            result = "[ERROR] " + e.toString();
            Log.v("myLog", "result: " + result);

        }return result;
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
