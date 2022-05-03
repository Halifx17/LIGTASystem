package com.example.ligtasystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LogIn(View view) {
        Intent intent = new Intent(MainActivity.this, Registration.class);
        startActivity(intent);
    }

    public void registerButton(View view) {
    }
}