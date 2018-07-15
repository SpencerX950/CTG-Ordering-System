package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/* Procedure:
 * 1.) TO VIEW THE CUSTOMER'S ORDER HISTORY, THE ACTIVITY MUST HAVE THE FOLLOWING TEXT FIELDS:
 * - ORDER ID (NON-EDITABLE)
 * - ORDER DATE (NON-EDITABLE)
 * - PRODUCT NAME (NON-EDITABLE)
 * - PRODUCT PRICE (NON-EDITABLE)
 * - SUBTOTAL (NON-EDITABLE)
 * - GRAND TOTAL (NON-EDITABLE)
 * - ORDER STATUS -> WILL BE CHANGED AFTER THE ADMIN SET THE CUSTOMERS ORDERS TO DELIVERED
 *
 * */
public class CustomerOrderHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_history);
    }
}
