package com.example.ligtasystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class InboxView extends AppCompatActivity {

    String  firstname, lastname, username, birthdate, email, address, phoneNumber, password, profileUri, type, concern;
    TextView inboxTypeText, inboxConcernText, inboxNameTv, inboxEmailTv, inboxAddressTv, inboxContactTv, inboxBirthdateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_view);


        inboxTypeText = findViewById(R.id.inboxTypeText);
        inboxConcernText = findViewById(R.id.inboxConcernText);



        Intent extraIntent = getIntent();
        concern = extraIntent.getStringExtra("concern");
        type = extraIntent.getStringExtra("type");
        firstname = extraIntent.getStringExtra("firstname");
        lastname = extraIntent.getStringExtra("lastname");
        username = extraIntent.getStringExtra("username");
        birthdate = extraIntent.getStringExtra("birthdate");
        email = extraIntent.getStringExtra("email");
        address = extraIntent.getStringExtra("address");
        phoneNumber = extraIntent.getStringExtra("phoneNumber");
        password = extraIntent.getStringExtra("password");
        profileUri = extraIntent.getStringExtra("profileUri");

        inboxTypeText.setText(type);
        inboxConcernText.setText(concern);

    }
}