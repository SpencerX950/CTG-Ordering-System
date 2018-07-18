package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AdminViewProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_products);

        /*Procedure:
        *1.) View Products is only for the Admin
        *2.) This activity can be viewed by:
        * - By Category of the Product
        * - All Product (ASC| DESC)
        * - By User
        * - By Order History
        * 3.) Intention to go to Add Products
        * */
    }
}
