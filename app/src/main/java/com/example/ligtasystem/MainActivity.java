package com.example.ligtasystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editEmail, editPassword;
    TextInputLayout editTextEmail, editTextPassword;
    Button LogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogInButton = findViewById(R.id.LogInButton);
        editEmail = findViewById(R.id.LogIn_Edit_Email);
        editPassword = findViewById(R.id.LogIn_Edit_Password);
        editTextEmail = (TextInputLayout) findViewById(R.id.EditText_Email);
        editTextPassword = (TextInputLayout) findViewById(R.id.EditText_Password);
        mAuth = FirebaseAuth.getInstance();
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