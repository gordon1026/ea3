package com.example.ea3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SettingsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_READ_STORAGE = 2;
    private TextView username;
    private ImageView profileImage;
    private SharedPreferences sharedPreferences;
    private String profileImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        username = findViewById(R.id.username);
        profileImage = findViewById(R.id.profile_image);
        sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        // Retrieve the username and profile image path from SharedPreferences
        String user = sharedPreferences.getString("username", "Default User");
        profileImagePath = sharedPreferences.getString("image_path", null);

        username.setText(user);

        // Load the image if the path exists
        if (profileImagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(profileImagePath);
            profileImage.setImageBitmap(bitmap);
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for storage permission
                startActivity(new Intent(SettingsActivity.this, A.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_map) {
                    startActivity(new Intent(SettingsActivity.this, MapActivity.class));
                    return true;
                } else if (itemId == R.id.action_home) {
                    startActivity(new Intent(SettingsActivity.this, HomePageActivity.class));
                    return true;
                } else if (itemId == R.id.action_take_photo) {
                    startActivity(new Intent(SettingsActivity.this, CheckActivity.class));
                    return true;
                }
                return false;
            }
        });

        // Set up the logout TextView click listener
        TextView logoutTextView = findViewById(R.id.logout);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutClick(v);
            }
        });
    }






    public void onLanguageClick(View view) {
        // Handle language setting click
        Toast.makeText(this, "Language Settings", Toast.LENGTH_SHORT).show();
    }

    public void onAboutClick(View view) {
        // Handle about click
        Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
    }

    public void onContactClick(View view) {
        // Handle contact us click
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/1234567890")); // WhatsApp link
        startActivity(intent);
    }

    public void onLogoutClick(View view) {
        // Handle logout click

        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();

        // Redirect to the login page
        Intent intent = new Intent(SettingsActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}