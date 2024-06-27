package com.example.ea3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etRePassword;
    private Button btnChange;
    private DBHelper dbHelper;
    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        btnChange = findViewById(R.id.btnChange);
        tvSignIn = findViewById(R.id.tvSignIn);
        dbHelper = new DBHelper(this);
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String rePassword = etRePassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "所有字段均为必填项", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "密码不匹配", Toast.LENGTH_SHORT).show();
                } else {
                    boolean result = dbHelper.updatePassword(username, password);
                    if (result) {
                        Toast.makeText(ChangePasswordActivity.this, "密码已成功更新", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "更新密码失败，请检查用户名", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}