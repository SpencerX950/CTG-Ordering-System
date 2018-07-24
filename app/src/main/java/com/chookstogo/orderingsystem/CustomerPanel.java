package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
// https://stackoverflow.com/questions/7578236/how-to-send-hashmap-value-to-another-activity-using-an-intent/7578313
public class CustomerPanel extends AppCompatActivity implements View.OnClickListener{

    TextView lblWelcomeCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_panel);
        lblWelcomeCustomer = (TextView) findViewById(R.id.lblWelcomeCustomer);
        findViewById(R.id.btnPurchase).setOnClickListener(this);
        findViewById(R.id.btnCart).setOnClickListener(this);
        findViewById(R.id.btnTransaction).setOnClickListener(this);
        findViewById(R.id.btnProfile).setOnClickListener(this);
        findViewById(R.id.btnSignout).setOnClickListener(this);

        setProfile();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnPurchase: GoToPurchase(); break;
            case R.id.btnCart:  GoToCart();break;
            case R.id.btnTransaction:  GoToTransaction(); break;
            case R.id.btnProfile:  GoToProfile(); break;
            case R.id.btnSignout:  GoToSignout(); break;
        }
    }

    private void GoToPurchase()
    {
        Intent b = getIntent();
        Intent intent = new Intent(CustomerPanel.this,CustomerAddToCart.class);
        intent.putExtra("User Map",b.getSerializableExtra("User Map"));
        startActivity(intent);
        finish();
    }

    private void GoToCart()
    {
        Intent b = getIntent();
        Intent intent = new Intent(CustomerPanel.this,CustomerViewCart.class);
        //intent.putExtra("User Map",b.getSerializableExtra("User Map"));
        startActivity(intent);
        finish();
    }

    private void GoToTransaction()
    {
        Intent b = getIntent();
        Intent intent = new Intent(CustomerPanel.this,CustomerOrderHistory.class);
        //intent.putExtra("User Map",b.getSerializableExtra("User Map"));
        startActivity(intent);
        finish();
    }

    private void GoToProfile()
    {
        Intent b = getIntent();
        Intent intent = new Intent(CustomerPanel.this,CustomerProfile.class);
        Log.d("User Map >>", String.valueOf(b.getSerializableExtra("User Map")));
        intent.putExtra("User Map",b.getSerializableExtra("User Map"));
        startActivity(intent);
        finish();
    }

    private void GoToSignout()
    {

    }

    private void setProfile()
    {
        Intent intent = getIntent();
        HashMap<String,String> UserMap = (HashMap<String,String>) intent.getSerializableExtra("User Map");
        lblWelcomeCustomer.setText("Hello," + String.valueOf(UserMap.get("Username")));
    }
}
