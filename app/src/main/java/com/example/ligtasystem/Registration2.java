package com.example.ligtasystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Registration2 extends AppCompatActivity {

    Button registrationButton, birthBtn;
    FirebaseAuth mAuth;
    TextInputLayout editTextUsername, editTextBirthDate, editTextAddress, editTextPhoneNumber;
    EditText editUsername, editBirthDate, editAddress, editPhoneNumber;
    DatePickerDialog datePickerDialog;

    public static int TIME_OUT = 3000;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String usernameExtra, emailExtra, firstnameExtra, lastnameExtra;
    Uri profileUriExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            Uri profilePicture = acct.getPhotoUrl();

            firstnameExtra = personGivenName;
            lastnameExtra = personFamilyName;
            usernameExtra = personName;
            profileUriExtra = profilePicture;
            emailExtra = personEmail;

        }


        initDatePicker();

        editUsername = findViewById(R.id.Edit_Username2);
        editAddress = findViewById(R.id.Edit_Address2);
        editPhoneNumber = findViewById(R.id.Edit_Phone_Number2);
        editBirthDate = findViewById(R.id.Edit_BirthDate2);

        editTextUsername = findViewById(R.id.EditText_Username2);
        editTextAddress = findViewById(R.id.EditText_Address2);
        editTextPhoneNumber = findViewById(R.id.EditText_Phone_Number2);
        editTextBirthDate = findViewById(R.id.EditText_BirthDate2);

        mAuth = FirebaseAuth.getInstance();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;
                String date = makeDateString(day, month, year);
                editBirthDate.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {

        return getMonthFormat(month)+" "+day+", "+year;
    }

    private String getMonthFormat(int month) {

        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUNE";
        if (month == 7)
            return "JULY";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        return "JAN";

    }

    public void openDatePicker(View view) {

        datePickerDialog.show();

    }

    public void registerFirebase(View view) {

        String firstname, lastname, username, address, phoneNumber, birthdate, email, profileUri, password;


        firstname = firstnameExtra;
        lastname = lastnameExtra;
        username = editUsername.getText().toString().trim();
        address = editAddress.getText().toString().trim();
        phoneNumber = editPhoneNumber.getText().toString().trim();
        birthdate = editBirthDate.getText().toString().trim();
        email = emailExtra;
        password = " ";
        profileUri = profileUriExtra.toString();


        if (username.isEmpty() && address.isEmpty() && phoneNumber.isEmpty() && birthdate.isEmpty()) {
            Toast.makeText(Registration2.this, "Fields are Required", Toast.LENGTH_LONG).show();

            editTextUsername.setError("Username is Required");
            editTextAddress.setError("Address is Required");
            editTextPhoneNumber.setError("Phone Number is Required");
            editTextBirthDate.setError("Birth Date is Required");
        } else if (username.isEmpty()) {
            Toast.makeText(Registration2.this, "Username is Required", Toast.LENGTH_LONG).show();

            editTextUsername.setError("Username is Required");
            editTextAddress.setError(null);
            editTextPhoneNumber.setError(null);
            editTextBirthDate.setError(null);
        }  else if (address.isEmpty()) {
            Toast.makeText(Registration2.this, "Address is Required", Toast.LENGTH_LONG).show();

            editTextUsername.setError(null);
            editTextAddress.setError("Address is Required");
            editTextPhoneNumber.setError(null);
            editTextBirthDate.setError(null);
        } else if (phoneNumber.isEmpty()) {
            Toast.makeText(Registration2.this, "Phone number is Required", Toast.LENGTH_LONG).show();

            editTextUsername.setError(null);
            editTextAddress.setError(null);
            editTextPhoneNumber.setError("Phone Number is Required");
            editTextBirthDate.setError(null);
        } else if (birthdate.isEmpty()) {
            Toast.makeText(Registration2.this, "Password is Required", Toast.LENGTH_LONG).show();

            editTextUsername.setError(null);
            editTextAddress.setError(null);
            editTextPhoneNumber.setError(null);
            editTextBirthDate.setError("Birth Date is Required");
        }else {

            User user = new User (firstname, lastname, username, birthdate, email, address, phoneNumber, password, profileUri);

            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                openDialog();
                            } else {
                                Toast.makeText(Registration2.this, "Failed Registration", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void openDialog(){

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Success");
        builder.setMessage("Registration Successful");
        builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
                finish();
                startActivity(intent);

            }
        });

        builder.show();

    }

    public void openDialog2(){

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Registration2.this);

        builder.setMessage("Cancel Registration?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Registration2.this, MainActivity.class);
                mAuth.signOut();
                gsc.signOut();
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    @Override
    public void onBackPressed() {
        openDialog2();
    }
}