package com.example.ligtasystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Registration extends AppCompatActivity {

    Button registrationButton, birthBtn;
    FirebaseAuth mAuth;
    TextInputLayout editTextFirstName, editTextMiddleName, editTextLastName, editTextUsername, editTextBirthDate, editTextEmail, editTextAddress, editTextPhoneNumber, editTextPassword;
    EditText editFirstName, editMiddleName, editLastName, editUsername, editBirthDate, editEmail, editAddress, editPhoneNumber, editPassword;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        initDatePicker();

        editFirstName = findViewById(R.id.Edit_First_Name);
        editMiddleName = findViewById(R.id.Edit_Middle_Name);
        editLastName = findViewById(R.id.Edit_Last_Name);
        editUsername = findViewById(R.id.Edit_Username);
        editEmail = findViewById(R.id.Edit_Email);
        editAddress = findViewById(R.id.Edit_Address);
        editPhoneNumber = findViewById(R.id.Edit_Phone_Number);
        editPassword = findViewById(R.id.Edit_Password);
        editBirthDate = findViewById(R.id.Edit_BirthDate);

        editTextFirstName = findViewById(R.id.EditText_First_Name);
        editTextMiddleName = findViewById(R.id.EditText_Middle_Name);
        editTextLastName = findViewById(R.id.EditText_Last_Name);
        editTextUsername = findViewById(R.id.EditText_Username);
        editTextEmail = findViewById(R.id.EditText_Email);
        editTextAddress = findViewById(R.id.EditText_Address);
        editTextPhoneNumber = findViewById(R.id.EditText_Phone_Number);
        editTextPassword = findViewById(R.id.EditText_Password);
        editTextBirthDate = findViewById(R.id.EditText_BirthDate);

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

        return getMonthFormat(month)+" "+day+" "+year;
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

        String firstname, middlename, lastname, username, email, address, phoneNumber, password, birthdate;

        firstname = editFirstName.getText().toString().trim();
        middlename = editMiddleName.getText().toString().trim();
        lastname = editLastName.getText().toString().trim();
        username = editUsername.getText().toString().trim();
        email = editEmail.getText().toString().trim();
        address = editAddress.getText().toString().trim();
        phoneNumber = editPhoneNumber.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        birthdate = editBirthDate.getText().toString().trim();


        if (firstname.isEmpty() && middlename.isEmpty() && lastname.isEmpty() && username.isEmpty()
                && email.isEmpty() && address.isEmpty() && phoneNumber.isEmpty() && password.isEmpty()&& birthdate.isEmpty()) {
            Toast.makeText(Registration.this, "Fields are Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError("First Name is Required");
            editTextMiddleName.setError("Middle Name is Required");
            editTextLastName.setError("Last Name is Required");
            editTextUsername.setError("Username is Required");
            editTextEmail.setError("Email is Required");
            editTextAddress.setError("Address is Required");
            editTextPhoneNumber.setError("Phone Number is Required");
            editTextPassword.setError("Password is Required\nMinimum of 6 characters");
            editTextBirthDate.setError("Birth Date is Required");
        } else if (firstname.isEmpty()) {
            Toast.makeText(Registration.this, "First name is Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError("First Name is Required");
            editTextMiddleName.setError(null);
            editTextLastName.setError(null);
            editTextUsername.setError(null);
            editTextEmail.setError(null);
            editTextAddress.setError(null);
            editTextPhoneNumber.setError(null);
            editTextPassword.setError(null);
            editTextBirthDate.setError(null);
        } else if (middlename.isEmpty()) {
            Toast.makeText(Registration.this, "Middle name is Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError(null);
            editTextMiddleName.setError("Middle Name is Required");
            editTextLastName.setError(null);
            editTextUsername.setError(null);
            editTextEmail.setError(null);
            editTextAddress.setError(null);
            editTextPhoneNumber.setError(null);
            editTextPassword.setError(null);
            editTextBirthDate.setError(null);
        } else if (lastname.isEmpty()) {
            Toast.makeText(Registration.this, "Last name is Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError(null);
            editTextMiddleName.setError(null);
            editTextLastName.setError("Last Name is Required");
            editTextUsername.setError(null);
            editTextEmail.setError(null);
            editTextAddress.setError(null);
            editTextPhoneNumber.setError(null);
            editTextPassword.setError(null);
            editTextBirthDate.setError(null);
        } else if (username.isEmpty()) {
            Toast.makeText(Registration.this, "Username is Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError(null);
            editTextMiddleName.setError(null);
            editTextLastName.setError(null);
            editTextUsername.setError("Username is Required");
            editTextEmail.setError(null);
            editTextAddress.setError(null);
            editTextPhoneNumber.setError(null);
            editTextPassword.setError(null);
            editTextBirthDate.setError(null);
        } else if (email.isEmpty()) {
            Toast.makeText(Registration.this, "Email is Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError(null);
            editTextMiddleName.setError(null);
            editTextLastName.setError(null);
            editTextUsername.setError(null);
            editTextEmail.setError("Email is Required");
            editTextAddress.setError(null);
            editTextPhoneNumber.setError(null);
            editTextPassword.setError(null);
            editTextBirthDate.setError(null);
        } else if (address.isEmpty()) {
            Toast.makeText(Registration.this, "Address is Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError(null);
            editTextMiddleName.setError(null);
            editTextLastName.setError(null);
            editTextUsername.setError(null);
            editTextEmail.setError(null);
            editTextAddress.setError("Address is Required");
            editTextPhoneNumber.setError(null);
            editTextPassword.setError(null);
            editTextBirthDate.setError(null);
        } else if (phoneNumber.isEmpty()) {
            Toast.makeText(Registration.this, "Phone number is Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError(null);
            editTextMiddleName.setError(null);
            editTextLastName.setError(null);
            editTextUsername.setError(null);
            editTextEmail.setError(null);
            editTextAddress.setError(null);
            editTextPhoneNumber.setError("Phone Number is Required");
            editTextPassword.setError(null);
            editTextBirthDate.setError(null);
        } else if (password.isEmpty()) {
            Toast.makeText(Registration.this, "Password is Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError(null);
            editTextMiddleName.setError(null);
            editTextLastName.setError(null);
            editTextUsername.setError(null);
            editTextEmail.setError(null);
            editTextAddress.setError(null);
            editTextPhoneNumber.setError(null);
            editTextPassword.setError("Password is Required\nMinimum of 6 characters");
            editTextBirthDate.setError(null);
        } else if (birthdate.isEmpty()) {
            Toast.makeText(Registration.this, "Password is Required", Toast.LENGTH_LONG).show();
            editTextFirstName.setError(null);
            editTextMiddleName.setError(null);
            editTextLastName.setError(null);
            editTextUsername.setError(null);
            editTextEmail.setError(null);
            editTextAddress.setError(null);
            editTextPhoneNumber.setError(null);
            editTextPassword.setError(null);
            editTextBirthDate.setError("Birth Date is Required");
        }else {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        User user = new User(firstname, middlename, lastname, username, birthdate, email, address, phoneNumber, password);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            openDialog();
                                        } else {
                                            Toast.makeText(Registration.this, "Failed Registration", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(Registration.this, "Failed to register", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
    }

    public void openDialog(){

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Success");
        builder.setMessage("Registration Successful");
        builder.setPositiveButton("Log In", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(intent);

            }
        });

        builder.show();

    }


}