package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/* Procedure:
 * 1.) TO VIEW CUSTOMER'S CART CREDENTIALS, THE ACTIVITY MUST HAVE THE FOLLOWING TEXT VIEW ON A LIST VIEW:
 * - PRODUCT NAME
 * - QUANTITY
 * - PRICE
 * 2.) TO EDIT CUSTOMER'S CART USE:
 * - INTENT CONTROL TO TRANSFER ITEM DATA TO EDITCART ACTIVITY
 * */
public class CustomerViewCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_cart);
    }
}
