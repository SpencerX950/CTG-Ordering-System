package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AdminViewProducts extends AppCompatActivity {
    String[] spinSortProducts = {"All","Category","Order History", "Customers"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_products);
    }
}
