package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CustomerOrder extends AppCompatActivity {
    /* Procedure:
     * 1.) TO FINALIZE THE CUSTOMER HIS ORDERS PLUS CREDENTIALS, THE ACTIVITY MUST HAVE THE FOLLOWING TEXT FIELDS:
     * - FULL NAME OF THE CUSTOMER
     * - PRODUCT NAME
     * - QUANTITY PER PRODUCT
     * - SUBTOTAL PER PRODUCT
     * - GRAND TOTAL OF ALL THE PRODUCTS
     * - ORDER NOW BUTTON
     * - CANCEL BUTTON
     *
     * 2) CONSTRAINTS
     *  2.1) IF THE CUSTOMER CANCELED THE ORDER:
     *      - THE CUSTOMER CART ITEMS WILL NOT BE REMOVED
     *  2.2) IF THE CUSTOMER ORDERS THE CURRENT PRODUCTS THAT IS IN THE CART:
     *      - DELETE THE ITEMS IN THE CURRENT CUSTOMERS CART
     *      - FOLLOWED BY PROMPT FOR SUCCESS
     *      - HEAD BACK TO __________ ACTIVITY
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);
    }
}
