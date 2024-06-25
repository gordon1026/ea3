package com.example.ea3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BMICalActivity extends AppCompatActivity {


    private EditText etHeight, etWeight;
    private TextView bmiValue, bmiCategory;
    private Button enterButton;

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

        float bmi = weight / (height * height);
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
}

