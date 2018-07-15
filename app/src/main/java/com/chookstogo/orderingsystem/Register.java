package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


}
