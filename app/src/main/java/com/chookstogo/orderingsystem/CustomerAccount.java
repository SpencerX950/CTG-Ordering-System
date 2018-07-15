package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CustomerAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_account);
    }

    /* Procedure:
    * 1.) TO CHANGE CUSTOMER CREDENTIALS, THE ACTIVITY MUST HAVE THE FOLLOWING TEXTFIELDS:
    * - USERNAME
    * - PASSWORD
    * - NAME -> FIRST,LAST
    * - MOBILE
    * - SAVE BUTTON
    * - CANCEL BUTTON
    * */
}
