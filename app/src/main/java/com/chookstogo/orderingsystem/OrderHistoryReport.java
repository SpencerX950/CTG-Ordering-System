package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/* PROCEDURE:
 * 1.) ONLY THE ADMIN CAN ACCESS THIS ACTIVITY
 * 2.)
 * */
public class OrderHistoryReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_report);
    }
}
