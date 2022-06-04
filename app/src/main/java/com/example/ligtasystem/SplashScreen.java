package com.example.ligtasystem;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    String userID, usernameExtra, firstnameExtra, lastnameExtra, emailExtra, addressExtra, phoneNumberExtra, profileUriExtra, passwordExtra, birthDateExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);





        mAuth = FirebaseAuth.getInstance();
        checkUser();

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

                    if(user!=null &&
                            user.username!=null &&
                            user.birthdate!=null &&
                            user.phoneNumber!=null &&
                            user.address!=null){
                        String username, firstname, lastname, email, address, phoneNumber, profileUri, password, birthdate;
                        username = user.username;
                        firstname = user.firstname;
                        lastname = user.lastname;
                        email = user.email;
                        address = user.address;
                        phoneNumber = user.phoneNumber;
                        profileUri = user.profileUri;
                        password = user.password;
                        birthdate = user.birthdate;
                        firstnameExtra = firstname;
                        lastnameExtra = lastname;
                        emailExtra = email;
                        addressExtra = address;
                        phoneNumberExtra = phoneNumber;
                        usernameExtra = username;
                        profileUriExtra = profileUri;
                        passwordExtra = password;
                        birthDateExtra = birthdate;

                        Log.e("Information", userID+" "+
                                usernameExtra+" "+
                                firstnameExtra+" "+
                                lastnameExtra+" "+
                                birthDateExtra+" "+
                                emailExtra+" "+
                                addressExtra+" "+
                                phoneNumberExtra+" "+
                                profileUriExtra+" "+
                                passwordExtra);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SplashScreen.this, GettingStarted.class);
                                intent.putExtra("username",usernameExtra);
                                intent.putExtra("firstname",firstnameExtra);
                                intent.putExtra("profileUri",profileUriExtra);
                                intent.putExtra("lastname",lastnameExtra);
                                intent.putExtra("email",emailExtra);
                                intent.putExtra("address",addressExtra);
                                intent.putExtra("phoneNumber",phoneNumberExtra);
                                intent.putExtra("password",passwordExtra);
                                intent.putExtra("birthDate",birthDateExtra);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }
                        }, TIME_OUT);

                    }
                    else{
                        startActivity(new Intent(getApplicationContext(),Registration2.class));
                        finish();
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