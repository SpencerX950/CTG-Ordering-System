package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
* 1) Get the User UID after Register on the Authentication
* 2) Then, send all credentials including the User UID to the database
*   Structure of the Folder in the Firebase
*   Users
*     + User UID
*       - Email
*       - Password
*       - First Name
*       - Last Name
*       - Mobile
*       - Username
*       + Roles
*           - Customer: true (For reading products only)
*           - Administrator: false (cannot change product)
*           - Author: true (for their profile only)
* 2.1) If the firebase does not accept null values, then assume that we will only send the Email and the Password on the Users Folder
*       for now.
*       - Thus, we will let the users add later their credentials and set new values to their folders ;)
* */
public class UserRegister extends AppCompatActivity implements View.OnClickListener{

    private ProgressBar progbarRegister;
    private EditText txtEmail,txtPassword,txtConfirm;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirm = findViewById(R.id.txtConfirmPass);
        progbarRegister = findViewById(R.id.progbarRegister);
        //Set Listeners
        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.lblSignIn).setOnClickListener(this);
        //Authentication
        mAuth = FirebaseAuth.getInstance();
        //Set progressbar invisible
        progbarRegister.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRegister: CheckRegisterCredentials(); break;
            case R.id.lblSignIn: GoToLoginActivity(); break;
        }
    }

    private void CheckRegisterCredentials()
    {
        final String email = txtEmail.getText().toString().trim();
        final String pass = txtPassword.getText().toString().trim();
        String confirm = txtConfirm.getText().toString().trim();
        Log.d("Email Trimmed >>", email);
        Log.d("Password Trimmed >>", pass);

        if(email.isEmpty())
           MissingFields(txtEmail);
        //Check if input email is Authentic Email
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            InvalidEmailPattern();
        if(pass.isEmpty())
            MissingFields(txtPassword);
        if(!pass.equals(confirm))
            PasswordNotMatch();
        if(pass.length() < 8)
            InvalidPassLength();

        progbarRegister.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) { // for first time user, we will setup their credentials
                    progbarRegister.setVisibility(View.GONE);
                    Toast.makeText(UserRegister.this, "User Sign up Success!", Toast.LENGTH_LONG).show();
                    Log.d("User UID >>", String.valueOf(task.getResult().getUser().getUid()));
                    SetupUserCredentials(String.valueOf(task.getResult().getUser().getUid()));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progbarRegister.setVisibility(View.GONE);
                Toast.makeText(UserRegister.this,"The user is already registered.\n We will proceed you to login",Toast.LENGTH_LONG).show();
                GoToLoginActivity();
            }
        });
    }

    private void SetupUserCredentials(String UserID)
    {
        dbRef = FirebaseDatabase.getInstance().getReference().child("User").child(String.valueOf(UserID));
        dbRef.child("Type").setValue("Customer");

        GoToLoginActivity();
    }

    private void GoToLoginActivity()
    {
        Intent intent = new Intent(UserRegister.this,UserLogin.class);
        startActivity(intent);
        finish();
    }

    private void MissingFields(EditText field)
    {
        field.setError("This Field is Required");
        field.requestFocus();
    }

    private void InvalidEmailPattern()
    {
        txtEmail.setError("Please Enter Valid Email");
        txtEmail.requestFocus();
    }

    private void InvalidPassLength()
    {
        txtPassword.setError("Minimum Length must be 8");
        txtPassword.requestFocus();
    }

    private void PasswordNotMatch()
    {
        txtPassword.setError("Password and Confirm not matched!");
        txtPassword.requestFocus();
    }
}

