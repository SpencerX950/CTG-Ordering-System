package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerOrderHistory extends AppCompatActivity implements View.OnClickListener{
    ListView listviewOrderHistory;
    DatabaseReference dbRef;
    ArrayList<String> ProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_history);

       setButtonListeners();
       setProductList();
    }

    private void setProductList()
    {
        dbRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    Log.d("Value >>",String.valueOf(data.child(data.getKey()).child("UserID").getKey()));
                    //ProductList.add(product.getName());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CustomerOrderHistory.this, String.valueOf(databaseError.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnBack: Back(); break;
        }
    }

    private void Back()
    {
        Intent intent = new Intent(CustomerOrderHistory.this,CustomerPanel.class);
        startActivity(intent);
        finish();
    }

    private void setButtonListeners()
    {
        findViewById(R.id.btnBack).setOnClickListener(this);
    }
}
