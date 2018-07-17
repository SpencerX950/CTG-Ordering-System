package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
public class Register extends AppCompatActivity {

    EditText txtFname, txtLname, txtEmail, txtMobile, txtUsername, txtPass;
    Button btnSignup;
    UserAuthentication auth = new UserAuthentication();
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Instantiation
        txtFname = (EditText) findViewById(R.id.txtFname);
        txtLname = (EditText) findViewById(R.id.txtLname);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtMobile = (EditText) findViewById(R.id.txtMobile);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        //set some
        btnSignup.setOnClickListener(new SignupListener());
    }

    class SignupListener implements View.OnClickListener{
        @Override
        public void onClick(View v)
        {
            Signup();
        }
    }
    public void Signup()
    {
        try
        {
            //get all credentials with checking values
            user.setFname(txtFname.getText().toString());
            user.setLname(txtLname.getText().toString());
            user.setMobile(txtMobile.getText().toString());
            user.setEmail(txtEmail.getText().toString());
            user.setUsername(txtUsername.getText().toString());
            user.setPass(txtPass.getText().toString());


            auth.setNewUser(user);
        }
        catch(NullPointerException e)
        {
            Log.d("Error:","Error");
        }

    }

}
