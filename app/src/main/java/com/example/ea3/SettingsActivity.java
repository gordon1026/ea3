package com.example.ea3;

import android.Manifest;
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
        String user = sharedPreferences.getString("userId", "Default User");
        profileImagePath = sharedPreferences.getString("profile_image_path", null);

        username.setText(user);

        if (profileImagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(profileImagePath);
            profileImage.setImageBitmap(bitmap);
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for storage permission
                if (ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                } else {
                    // Open gallery to pick an image
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE);
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                try {
                    // Get the image as a Bitmap
                    InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    profileImage.setImageBitmap(bitmap);

                    // Save the image locally
                    File imageFile = saveImageToLocal(bitmap);
                    if (imageFile != null) {
                        profileImagePath = imageFile.getAbsolutePath();

                        // Save the image path in SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("profile_image_path", profileImagePath);
                        editor.apply();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File saveImageToLocal(Bitmap bitmap) {
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ProfileImages");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "profile_image_" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(directory, fileName);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Open gallery to pick an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();

        // Redirect to the login page
        Intent intent = new Intent(SettingsActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}