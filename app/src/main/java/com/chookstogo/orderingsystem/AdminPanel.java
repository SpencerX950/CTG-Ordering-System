package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class AdminPanel extends AppCompatActivity {

    TextView lblWelcomeAdmin;
    Button btnView,btnAdd,btnEdit,btnDelete,btnBack;
    HashMap<String,String> UserMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        //Instantiate objects
        btnView = findViewById(R.id.btnView);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);
        lblWelcomeAdmin = findViewById(R.id.lblWelcomeAdmin);

        setButtonListeners();
        setWelcomeBanner();
    }

    class AdminProcessListener implements View.OnClickListener
    {
        @Override
        public void onClick(View button)
        {
            switch (button.getId())
            {
                case R.id.btnAdd : OpenAddItem(); break;
                case R.id.btnEdit : OpenEditItem(); break;
                case R.id.btnView : OpenViewItems(); break;
                case R.id.btnDelete : OpenDeleteItem(); break;
                case R.id.btnBack: break;
                default: break;
            }
        }
    }

    public void setButtonListeners()
    {
        btnView.setOnClickListener(new AdminProcessListener());
        btnAdd.setOnClickListener(new AdminProcessListener());
        btnEdit.setOnClickListener(new AdminProcessListener());
        btnDelete.setOnClickListener(new AdminProcessListener());
        btnBack.setOnClickListener(new AdminProcessListener());
    }

    public void OpenAddItem()
    {
        Intent intent = new Intent(AdminPanel.this,AdminAddProducts.class);
        startActivity(intent);
        finish();
    }

    public void OpenEditItem()
    {
        Intent intent = new Intent(AdminPanel.this,AdminEditProducts.class);
        startActivity(intent);
        finish();
    }

    public void OpenViewItems()
    {
        Intent intent = new Intent(AdminPanel.this,AdminViewProducts.class);
        startActivity(intent);
        finish();
    }

    public void OpenDeleteItem()
    {
        Intent intent = new Intent(AdminPanel.this,AdminDeleteProduct.class);
        startActivity(intent);
        finish();
    }

    public void setWelcomeBanner()
    {
        Intent intent = getIntent();
        UserMap = (HashMap<String,String>) intent.getSerializableExtra("User Map");
        Log.d("User Map in Admin Panel", String.valueOf(UserMap));
        if(!UserMap.get("Fname").isEmpty())
            lblWelcomeAdmin.setText("Hello," + String.valueOf(UserMap.get("Fname")));
        else
            lblWelcomeAdmin.setText("Hello,Admin");
    }
}
