package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CustomerViewCart extends AppCompatActivity implements View.OnClickListener{

    DatabaseReference dbRef;
    ArrayList<Product> CartMap;
    TextView lblGrandTotal,lblTotalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_cart);
        lblGrandTotal = findViewById(R.id.lblGrandtotal);
        lblTotalItems = findViewById(R.id.lblTotalItems);

        setButtonListeners();
        //Set Order List to be Ordered and Delivered!
        SetOrderList();
    }
    @SuppressWarnings("unchecked")
    private void SetOrderList()
    {
        // Get Instances from FireBase
    }

    private void Order()
    {
       //
    }


    private void CancelOrder()
    {
        Intent intent = new Intent(CustomerViewCart.this,CustomerAddToCart.class);
        startActivity(intent);
        finish();
    }

    private void setButtonListeners()
    {
        findViewById(R.id.btnOrder).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnOrder: Order(); break;
            case R.id.btnCancel: CancelOrder(); break;
        }
    }
}
