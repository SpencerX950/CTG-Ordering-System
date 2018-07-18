package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/* Procedure:
 * 1.) TO EDIT CART CREDENTIALS, THE ACTIVITY MUST HAVE THE FOLLOWING TEXTVIEW/TEXTFIELDS/BUTTONS:
 * - PRODUCT NAME (NON-EDITABLE)
 * - PRICE (NON-EDITABLE)
 * - CATEGORY (NON-EDITABLE)
 * - QUANTITY (EDITABLE)
 * - SUBTOTAL (NON-EDITABLE) -> PER PRODUCT
 * - GRANDTOTAL (NON-EDITABLE) -> ALL OVER TOTAL FOR THE PRODUCTS INSIDE THE CART
 * - SAVE BUTTON
 * - CANCEL BUTTON
 * */
public class CustomerEditCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit_cart);
    }
}
