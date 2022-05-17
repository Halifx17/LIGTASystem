package com.example.ligtasystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GettingStarted extends AppCompatActivity {


    TextView getStartedUsername;
    String extraUsername;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dbReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);



        getStartedUsername = findViewById(R.id.getStartedUsername);
        Intent extraIntent = getIntent();
        extraUsername = extraIntent.getStringExtra("username");
        getStartedUsername.setText("Hi, "+extraUsername+"!");
        mAuth=FirebaseAuth.getInstance();


    }



    @Override
    public void onBackPressed() {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(GettingStarted.this);


        builder.setMessage("Log Out?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(GettingStarted.this, MainActivity.class);
                mAuth.signOut();
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    public void goToHome(View view) {
        Intent intent = new Intent(GettingStarted.this,Home.class);
        startActivity(intent);
    }
}