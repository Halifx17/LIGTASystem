package com.example.ligtasystem;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editEmail, editPassword;
    TextInputLayout editTextEmail, editTextPassword;
    Button logInButton, googleButton;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        logInButton = findViewById(R.id.LogInButton);
        googleButton = findViewById(R.id.googleButton);
        editEmail = findViewById(R.id.LogIn_Edit_Email);
        editPassword = findViewById(R.id.LogIn_Edit_Password);
        editTextEmail = (TextInputLayout) findViewById(R.id.EditText_Email);
        editTextPassword = (TextInputLayout) findViewById(R.id.EditText_Password);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        mAuth = FirebaseAuth.getInstance();

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }


/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

 */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.d(TAG, "onSuccess: Logged In");
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                String uid = firebaseUser.getUid();
                String email = firebaseUser.getEmail();

                Log.d(TAG, "onSuccess: Email "+email);
                Log.d(TAG, "onSuccess: UID "+uid);

                if (authResult.getAdditionalUserInfo().isNewUser()){
                    Toast.makeText(MainActivity.this,"Account Created "+email,Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d(TAG, "onSuccess: Existing user "+email);
                    Toast.makeText(MainActivity.this,"Existing user "+email,Toast.LENGTH_SHORT).show();

                }

                Intent intent = new Intent(MainActivity.this,SplashScreen.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d(TAG, "onFailure: Loggin Failed "+e.getMessage());

            }
        });
    }

    void navigateToSecondActivity(){
        Toast.makeText(MainActivity.this, "Log In Successful", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this,SplashScreen.class);
        startActivity(intent);
        finish();
    }

    public void LogIn(View view) {

        String email, password;
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        if(email.isEmpty()&&password.isEmpty()){
            Toast.makeText(MainActivity.this, "Fields cannot be Empty", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Email is required");
            editTextPassword.setError("Password is required");
        }
        else if(email.isEmpty()){
            Toast.makeText(MainActivity.this, "Email cannot be Empty", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Password is required");
            editTextPassword.setError(null);
        }

        else if(password.isEmpty()){
            Toast.makeText(MainActivity.this, "Password cannot be Empty", Toast.LENGTH_SHORT).show();
            editTextPassword.setError("Password is required");
            editTextEmail.setError(null);
        }

        else{
            editTextEmail.setError(null);
            editTextPassword.setError(null);


            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(MainActivity.this, "Log In Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(MainActivity.this, "Email or Password is incorrect", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }

    }

    public void registerButton(View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);

        builder.setMessage("Exit Application?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();
                moveTaskToBack(true);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }
}