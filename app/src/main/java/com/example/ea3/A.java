package com.example.ea3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class A extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;

    private ImageView avatarImageView;
    private Bitmap bitmap;
    private Uri filePath;
    private Button buttonChoose, buttonUpload, back;
    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        avatarImageView = findViewById(R.id.imageView);
        buttonChoose = findViewById(R.id.buttonChoose);
        buttonUpload = findViewById(R.id.buttonUpload);
        back = findViewById(R.id.back);
        dbHelper = new DBHelper(this);
        sharedPreferences = getSharedPreferences("UserPref", MODE_PRIVATE);

        requestStoragePermission();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(A.this, SettingsActivity.class));
            }
        });

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) {
                    saveImageLocally();
                } else {
                    Toast.makeText(A.this, "Please choose an avatar first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadAvatar();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                avatarImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveImageLocally() {
        // Save the image to local storage
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ProfileImages");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "profile_image_" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(directory, fileName);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            String imagePath = imageFile.getAbsolutePath();

            // Save the image path in the database
            String username = sharedPreferences.getString("username", "default_user");
            boolean isUpdated = dbHelper.updateImagePath(username, imagePath);

            if (isUpdated) {
                Toast.makeText(this, "Avatar updated successfully", Toast.LENGTH_SHORT).show();

                // Save the image path in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("image_path", imagePath);
                editor.apply();

                loadAvatar();
                startActivity(new Intent(A.this, SettingsActivity.class));
            } else {
                Toast.makeText(this, "Failed to update avatar", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Explain why you need this permission
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadAvatar() {
        String username = sharedPreferences.getString("username", "default_user");
        Cursor cursor;
        cursor = dbHelper.getUser(username);
        if (cursor.moveToFirst()) {
            String imagePath = cursor.getString(cursor.getColumnIndex("image_path"));
            if (imagePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                avatarImageView.setImageBitmap(bitmap);
            }
        }
        cursor.close();
    }
}