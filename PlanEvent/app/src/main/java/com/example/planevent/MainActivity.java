package com.example.planevent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView tvSelectedDate;
    private Button btnSelectDate, btnSubmit;
    private RadioGroup radioGroupEvent;
    private CheckBox cbCatering, cbPhotography, cbLiveMusic;
    private ImageButton btnSelectImage;
    private Uri imageUri;
    private int selectedYear, selectedMonth, selectedDay;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnSelectDate=findViewById(R.id.btnSelectDate);
        btnSelectImage=findViewById(R.id.btnSelectImage);
        btnSubmit=findViewById(R.id.btnSubmit);
        tvSelectedDate=findViewById(R.id.tvSelectedDate);
        cbCatering=findViewById(R.id.cbCatering);
        cbPhotography=findViewById(R.id.cbPhotography);
        cbLiveMusic=findViewById(R.id.cbLiveMusic);
        radioGroupEvent=findViewById(R.id.radioGroupEvent);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChoose();
            }
        });

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processForm();
            }
        });

    }
    private void openImageChoose()
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) { // Fix & to &&
            imageUri = data.getData();
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                btnSelectImage.setImageBitmap(bmp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showDatePicker()
    {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this,(View,selectedYear,selectedMonth,selectedDate) -> {
            tvSelectedDate.setText("Date:"+selectedYear+"/"+(selectedMonth+1)+"/"+selectedDate);
        },year,month,day).show();


    }

    private void processForm()
    {
        int selectedEventId=radioGroupEvent.getCheckedRadioButtonId();
        String eventType=selectedEventId !=1 ? ((RadioButton)findViewById(selectedEventId)).getText().toString() : "Not Selected";
        String services="";

        if(cbCatering.isChecked()) services+="Catering ";
        if(cbLiveMusic.isChecked()) services+="Live Music ";
        if(cbPhotography.isChecked()) services+="Photography ";

        Toast.makeText(this,eventType+"\n"+services,Toast.LENGTH_LONG).show();
    }
}