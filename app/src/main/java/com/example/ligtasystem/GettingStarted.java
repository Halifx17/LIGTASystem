package com.example.ligtasystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class GettingStarted extends AppCompatActivity {


    TextView getStartedUsername;
    String extraUsername, extraFirstname, extraMiddlename, extraLastname, extraEmail, extraAddress, extraPhonenumber;
    FirebaseAuth mAuth;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView profilePicture;
    Uri profileUri;


    FirebaseUser user;
    DatabaseReference dbReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        profilePicture = findViewById(R.id.profile_icon);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            Uri personProfile = acct.getPhotoUrl();
            String personEmail = acct.getEmail();
            profileUri = personProfile;




        }



        getStartedUsername = findViewById(R.id.getStartedUsername);
        Intent extraIntent = getIntent();
        extraUsername = extraIntent.getStringExtra("username");
        extraFirstname = extraIntent.getStringExtra("firstname");
        extraMiddlename = extraIntent.getStringExtra("middlename");
        extraLastname = extraIntent.getStringExtra("lastname");
        extraEmail = extraIntent.getStringExtra("email");
        extraAddress = extraIntent.getStringExtra("address");
        extraPhonenumber = extraIntent.getStringExtra("phoneNumber");
        getStartedUsername.setText("Hi, " + extraUsername + "!");
        mAuth = FirebaseAuth.getInstance();

        Log.e("Details",extraFirstname+" "+extraMiddlename+" "+extraLastname
                +" "+extraEmail+" "+extraAddress+" "+extraPhonenumber);

        Picasso.get()
                .load(profileUri)
                .placeholder(R.drawable.person)
                .into(profilePicture);





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
                gsc.signOut();
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