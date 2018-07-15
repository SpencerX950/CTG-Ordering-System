package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/*PROCEDURE:
* 1.) USE FIREBASE AUTHENTICATION
* 2.)
* */
public class Login extends AppCompatActivity {

    TextView lblSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //set all required fields to connect
        lblSignup = findViewById(R.id.lblSignUp);

        //Create Clickable "Sign up" text view
        SpannableString signup = new SpannableString(lblSignup.getText().toString());
        ClickableSpan clickSignup = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class );
                startActivity(intent);
                finish();
            }
        };
        signup.setSpan(clickSignup,24,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        lblSignup.setMovementMethod(LinkMovementMethod.getInstance());
    }


}
