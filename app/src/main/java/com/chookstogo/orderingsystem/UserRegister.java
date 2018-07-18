package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/* PROCEDURE:
 * 1.) TO REGISTER CUSTOMER CREDENTIALS, THE ACTIVITY MUST HAVE THE FOLLOWING TEXT FIELDS/BUTTONS:
 * - USERNAME
 * - PASSWORD
 * - NAME: FIRST,LAST
 * - EMAIL
 * - SIGN UP BUTTON
 *
 * 2.) AFTER CLICKING SIGN UP BUTTON:
 *  2.1) IF THE USER DOES NOT MET REQUIREMENTS:
 *      - SEND ERROR MESSAGE INDICATING SPECIFIC ERROR
 *  2.2) IF THE USER DOES NOT ENTER EMAIL FORMAT "@<ANY EMAIL>.COM":
 *       - SEND ERROR MESSAGE INDICATING SPECIFIC ERROR
 *  2.3) IF SUCCESS:
 *      - THE ACTIVITY WILL USE INTENT TO HEAD BACK TO THE LOGIN ACTIVITY
 * NOTE: OR JUST USE FIREBASE REGISTRATION AUTHENTICATION
 * */
public class UserRegister extends AppCompatActivity {

    EditText txtFname, txtLname, txtEmail, txtMobile, txtUsername, txtPass;
    Button btnRegister;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        //Instantiation
        txtFname = findViewById(R.id.txtFname);
        txtLname = findViewById(R.id.txtLname);
        txtEmail = findViewById(R.id.txtEmail);
        txtMobile = findViewById(R.id.txtMobile);
        txtUsername = findViewById(R.id.txtUsername);
        txtPass = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);

    }

    public void RegisterUser(View v)
    {
       try
       {
         user.setUsername(txtUsername.getText().toString());
         user.setPass(txtPass.getText().toString());
         user.setEmail(txtEmail.getText().toString());
         user.setFname(txtLname.getText().toString());
         user.setLname(txtLname.getText().toString());
         user.setMobile(txtMobile.getText().toString());
         user.setType("Customer");
         // In this part, it quickly gets the boolean before even having entering the user.RegisterUser() method, though the user is successfully registered. Please fix this @Leona
         isRegisterSuccess(user.RegisterUser());
       }
       catch(NullPointerException e)
        {
            Toast.makeText(UserRegister.this, "Missing Fields!", Toast.LENGTH_LONG).show();
        }
    }


    public void isRegisterSuccess(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(UserRegister.this, "Registration Success!", Toast.LENGTH_LONG).show();
            GoToLogin();
        } else {
            Toast.makeText(UserRegister.this, "Registration Failed!", Toast.LENGTH_LONG).show();
        }
    }

    public void GoToLogin()
    {
        Intent intent = new Intent(UserRegister.this, UserLogin.class);
        startActivity(intent);
        finish();
    }
}
