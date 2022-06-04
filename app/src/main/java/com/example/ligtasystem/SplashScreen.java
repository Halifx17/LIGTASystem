package com.example.ligtasystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {

    public static int TIME_OUT = 3000;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dbReference;
    String userID, usernameExtra, firstnameExtra, middlenameExtra, lastnameExtra, emailExtra, addressExtra, phoneNumberExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);





        mAuth = FirebaseAuth.getInstance();
        checkUser();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, GettingStarted.class);
                intent.putExtra("username",usernameExtra);
                intent.putExtra("firstname",firstnameExtra);
                intent.putExtra("middlename",middlenameExtra);
                intent.putExtra("lastname",lastnameExtra);
                intent.putExtra("email",emailExtra);
                intent.putExtra("address",addressExtra);
                intent.putExtra("phoneNumber",phoneNumberExtra);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, TIME_OUT);



    }

    private void checkUser() {

        user = mAuth.getCurrentUser();

        if (user == null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        else{

            dbReference = FirebaseDatabase.getInstance().getReference("Users");
            userID = user.getUid();
            dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    if(user!=null){
                        String username, firstname, middlename, lastname, email, address, phoneNumber;
                        username = user.username;
                        firstname = user.firstname;
                        middlename = user.middlename;
                        lastname = user.lastname;
                        email = user.email;
                        address = user.address;
                        phoneNumber = user.phoneNumber;
                        firstnameExtra = firstname;
                        middlenameExtra = middlename;
                        lastnameExtra = lastname;
                        emailExtra = email;
                        addressExtra = address;
                        phoneNumberExtra = phoneNumber;
                        usernameExtra = username;

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(SplashScreen.this,"An Error has Occurred", Toast.LENGTH_LONG).show();

                }
            });

        }


    }


}