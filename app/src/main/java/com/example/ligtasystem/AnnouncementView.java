package com.example.ligtasystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AnnouncementView extends AppCompatActivity {

    String date, about, title;
    TextView dateText, aboutText, titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_view);

        aboutText = findViewById(R.id.aboutText);
        dateText = findViewById(R.id.dateText);
        titleText = findViewById(R.id.titleText);

        Intent extraIntent = getIntent();
        date = extraIntent.getStringExtra("dateExtra");
        about = extraIntent.getStringExtra("aboutExtra");
        title = extraIntent.getStringExtra("titleExtra");

        aboutText.setText(about);
        dateText.setText(date);
        titleText.setText(title);


    }
}