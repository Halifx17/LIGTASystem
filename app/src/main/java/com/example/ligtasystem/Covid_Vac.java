package com.example.ligtasystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Covid_Vac extends AppCompatActivity {

    TextInputLayout editTextTitle, editTextWhatTxt;
    TextInputEditText editTitle, editWhatTxt;
    DatabaseReference referenceId;
    String userID, extraUsername, extraFirstname, extraLastname, extraEmail, extraAddress, extraPhoneNumber, extraProfileUri, extraBirthDate, extraPassword;
    long maxId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_vac);

        editTextTitle = findViewById(R.id.EditText_title);
        editTextWhatTxt = findViewById(R.id.EditText_title);

        editTitle = findViewById(R.id.Edit_title);
        editWhatTxt = findViewById(R.id.Edit_whatTxt);

        Intent extraIntent = getIntent();
        userID = extraIntent.getStringExtra("userID");
        extraUsername = extraIntent.getStringExtra("username");
        extraFirstname = extraIntent.getStringExtra("firstname");
        extraLastname = extraIntent.getStringExtra("lastname");
        extraEmail = extraIntent.getStringExtra("email");
        extraAddress = extraIntent.getStringExtra("address");
        extraPhoneNumber = extraIntent.getStringExtra("phoneNumber");
        extraProfileUri = extraIntent.getStringExtra("profileUri");
        extraBirthDate = extraIntent.getStringExtra("birthDate");
        extraPassword = extraIntent.getStringExtra("password");
        Log.e("Output"," "+userID);


        referenceId = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Posts");


        referenceId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    maxId = (snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }


    public void postFirebase(View view) {

        String title, content;

        title = editTitle.getText().toString().trim();
        content = editWhatTxt.getText().toString().trim();

        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(Covid_Vac.this, "Fields are Required", Toast.LENGTH_LONG).show();
            editTextTitle.setError("Field is Required");
            editTextWhatTxt.setError("Field is Required");

        } else if(title.isEmpty()) {
            Toast.makeText(Covid_Vac.this, "Fields are Required", Toast.LENGTH_LONG).show();
            editTextTitle.setError("Field is Required");
            editTextWhatTxt.setError(null);

        } else if(content.isEmpty()) {
            Toast.makeText(Covid_Vac.this, "Fields are Required", Toast.LENGTH_LONG).show();
            editTextTitle.setError(null);
            editTextWhatTxt.setError("Field is Required");

        } else {
            Post post = new Post(title,content);


            referenceId.child(String.valueOf(maxId+1)).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        openDialog();
                    } else {
                        Toast.makeText(Covid_Vac.this, "Failed Registration", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }



    }

    public void openDialog(){

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Success");
        builder.setMessage("Announcement Posted");
        builder.setPositiveButton("Return", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getApplicationContext(),Home.class);
                intent.putExtra("username",extraUsername);
                intent.putExtra("firstname",extraFirstname);
                intent.putExtra("profileUri",extraProfileUri);
                intent.putExtra("lastname",extraLastname);
                intent.putExtra("email",extraEmail);
                intent.putExtra("address",extraAddress);
                intent.putExtra("phoneNumber",extraPhoneNumber);
                intent.putExtra("password",extraPassword);
                intent.putExtra("birthDate",extraBirthDate);
                intent.putExtra("userID",userID);
                finish();
                startActivity(intent);

            }
        });

        builder.show();
    }
}