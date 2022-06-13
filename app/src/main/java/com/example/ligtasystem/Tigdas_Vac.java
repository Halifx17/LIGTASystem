package com.example.ligtasystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

public class Tigdas_Vac extends AppCompatActivity {

    TextInputLayout editTextTitle, editTextWhatTxt;
    TextInputEditText editTitle, editWhatTxt;
    DatabaseReference referenceId;
    String userID, extraUsername, extraFirstname, extraLastname, extraEmail, extraAddress, extraPhoneNumber, extraProfileUri, extraBirthDate, extraPassword;
    long maxId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tigdas_vac);
    }
}