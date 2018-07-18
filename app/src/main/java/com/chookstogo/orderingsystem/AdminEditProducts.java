package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/* PROCEDURE:
 * 1.) ONLY THE ADMIN CAN ACCESS THIS ACTIVITY
 * 2.) TO ADD PRODUCT CREDENTIALS, THE ACTIVITY MUST HAVE THE FOLLOWING TEXT FIELDS/BUTTONS:
 * - PRODUCT NAME
 * - PRICE
 * - CATEGORY
 * - SAVE BUTTON
 * - CANCEL BUTTON
 *
 * 2.) After clicking SAVE Button:
 * - the admin will be prompted using toast or what Sir Salacsacan does
 *  - admin will be destined back to the View Product Activity
 * */
public class AdminEditProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_products);
    }
}
