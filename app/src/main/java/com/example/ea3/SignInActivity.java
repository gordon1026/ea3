package com.example.ea3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSignIn;
    private DBHelper dbHelper;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
        dbHelper = new DBHelper(this);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Email and Password are required", Toast.LENGTH_SHORT).show();
                } else {
                    if (dbHelper.checkUser(email, password)) {
                        Cursor cursor = dbHelper.getUser(email);
                        if (cursor.moveToFirst()) {
                            int userId = cursor.getInt(cursor.getColumnIndex("id"));
                            String username = cursor.getString(cursor.getColumnIndex("username"));

                            // Save user data in SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("userId", userId);
                            editor.putString("username", username);
                            editor.putString("email", email);

                            // Optionally, you can also save the profile image URI if available
                            // String profileImageUri = ...; // Get the URI from your database or other source
                            // editor.putString("profile_image_uri", profileImageUri);

                            editor.apply();

                            Intent intent = new Intent(SignInActivity.this, HomePageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        cursor.close();
                    } else {
                        Toast.makeText(SignInActivity.this, "Invalid Account", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}