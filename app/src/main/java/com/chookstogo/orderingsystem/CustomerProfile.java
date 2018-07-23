package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.HashMap;

public class CustomerProfile extends AppCompatActivity {

    EditText txtFname,txtLname,txtEmail,txtMobile,txtPass,txtConfirmPass,txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        txtFname = (EditText) findViewById(R.id.txtFname);
        txtLname = (EditText) findViewById(R.id.txtLname);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtConfirmPass = (EditText) findViewById(R.id.txtConfirmPass);
        txtMobile = (EditText) findViewById(R.id.txtMobile);

        setProfile();
    }

    private void setProfile()
    {
        Intent intent = getIntent();
        HashMap<String,String> UserMap = (HashMap<String,String>) intent.getSerializableExtra("User Map");

        txtFname.setText(String.valueOf(UserMap.get("Fname")));
        txtLname.setText(String.valueOf(UserMap.get("Lname")));
        txtMobile.setText(String.valueOf(UserMap.get("Mobile")));
        txtUsername.setText(String.valueOf(UserMap.get("Username")));
        txtEmail.setText(String.valueOf(UserMap.get("Email")));
        txtPass.setText(String.valueOf(UserMap.get("Password")));
        txtConfirmPass.setText(String.valueOf(UserMap.get("Password")));
    }
}
