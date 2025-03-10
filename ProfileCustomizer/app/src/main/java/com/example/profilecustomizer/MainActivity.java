package com.example.profilecustomizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText etName;
    private Button btnChooseImage;
    private Button btnSaveProfile;
    private ImageView ivChooseImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        ivChooseImage = findViewById(R.id.ivChooseImage);

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveProfile();
            }
        });


    }

    private void SaveProfile() {
        String name=etName.getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(this, "Please enter your Name", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Your Profile Saved", Toast.LENGTH_LONG).show();
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) { // Fix & to &&
            imageUri = data.getData();
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivChooseImage.setImageBitmap(bmp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}