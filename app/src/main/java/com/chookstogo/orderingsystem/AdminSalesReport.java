package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/* PROCEDURE:
 * 1.) ONLY THE ADMIN CAN ACCESS THIS ACTIVITY
 * 2.) TO VIEW SALES REPORT, THE ACTIVITY MUST HAVE THE FOLLOWING TEXT FIELDS/BUTTONS:
 * - PRODUCT NAME
 * - PRICE
 * 3.) TO FILTER REPORT:
 * - BY DAY
 * - BY MONTH
 * - BY YEAR
 * */
public class AdminSalesReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sales_report);
    }
}
