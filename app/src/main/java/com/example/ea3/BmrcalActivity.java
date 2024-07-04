package com.example.ea3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class BmrcalActivity extends AppCompatActivity {
    private Button btnSubmit, btnHome;
    private EditText editWeight, editHeight, editAge;
    private ImageView btnBackTo;
    private TextView textViewBMR, textViewTDEE;
    private RadioGroup rgActivity, rgTarget, rgGender;

    private double userWeight;
    private double userHeight;
    private double userAge;
    private double userBMR;
    private double userTDEE;
    private double activityFactor;
    private double targetFactor;
    private double genderFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmrcal);

        editWeight = findViewById(R.id.editweight);
        editHeight = findViewById(R.id.editheight);
        editAge = findViewById(R.id.editage);
        textViewBMR = findViewById(R.id.textViewBMR);
        textViewTDEE = findViewById(R.id.textViewTDEE);
        btnHome = findViewById(R.id.btnhome);
        btnSubmit = findViewById(R.id.btnsubmit);
        btnBackTo = findViewById(R.id.BackTo);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(BmrcalActivity.this, HomePageActivity.class);
            startActivity(intent);
        });
        btnBackTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        rgGender = findViewById(R.id.rgGender);
        rgGender.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = group.findViewById(checkedId);
            String selectedGender = selectedButton.getText().toString();
            if (selectedGender.equals("Male")) {
                genderFactor = 5.0;
            } else if (selectedGender.equals("Female")) {
                genderFactor = -161.0;
            }
        });

        rgTarget = findViewById(R.id.rgTarget);
        rgTarget.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = group.findViewById(checkedId);
            String selectedTarget = selectedButton.getText().toString();
            if (selectedTarget.equals("Gain Weight")) {
                targetFactor = 200.0;
            } else if (selectedTarget.equals("Generally")) {
                targetFactor = 0.0;
            } else if (selectedTarget.equals("Lose Weight")) {
                targetFactor = -200.0;
            }
        });

        rgActivity = findViewById(R.id.rgActivity);
        rgActivity.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = group.findViewById(checkedId);
            String selectedActivity = selectedButton.getText().toString();
            if (selectedActivity.equals("Sedentary")) {
                activityFactor = 1.2;
            } else if (selectedActivity.equals("Light")) {
                activityFactor = 1.4;
            } else if (selectedActivity.equals("Medium")) {
                activityFactor = 1.6;
            } else if (selectedActivity.equals("High")) {
                activityFactor = 1.8;
            } else if (selectedActivity.equals("Very High")) {
                activityFactor = 2.0;
            }
        });

        btnSubmit.setOnClickListener(v -> {
            if (validateUserInput()) {
                calculateBMRAndTDEE();
                updateResults();
            }
        });
    }

    private boolean validateUserInput() {
        if (TextUtils.isEmpty(editWeight.getText().toString())) {
            Toast.makeText(this, "Please enter your weight", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(editHeight.getText().toString())) {
            Toast.makeText(this, "Please enter your height", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(editAge.getText().toString())) {
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show();
            return false;
        }
        userWeight = Double.parseDouble(editWeight.getText().toString());
        userHeight = Double.parseDouble(editHeight.getText().toString());
        userAge = Double.parseDouble(editAge.getText().toString());
        return true;
    }

    private void calculateBMRAndTDEE() {
        userBMR = (10.0 * userWeight) + (6.25 * userHeight) - (5.0 * userAge) + genderFactor;
        userTDEE = (userBMR * activityFactor) + targetFactor;
    }

    private void updateResults() {
        textViewBMR.setText(String.format("BMR: %.2f", userBMR));
        textViewTDEE.setText(String.format("TDEE: %.2f", userTDEE));
    }
}