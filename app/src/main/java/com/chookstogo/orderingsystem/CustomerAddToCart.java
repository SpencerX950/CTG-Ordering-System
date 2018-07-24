package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
/*
* https://stackoverflow.com/questions/3004713/get-content-uri-from-file-path-in-android
* */
public class CustomerAddToCart extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    DatabaseReference dbRef;
    StorageReference storageRef;
    Product product = new Product();
    ListView ProductView;
    ArrayList<String> ProductList = new ArrayList<>(); // List of Product Name that will be populated in listview
    ArrayList<HashMap<String,String>> ProductMap = new ArrayList<>(); //All Product List
    ArrayList<HashMap<String,String>> SelectedItems = new ArrayList<>(); // List of selected items only, ProductId,ProductName,Qty
    ImageView imgProduct;
    TextView lblProductName,lblPrice,lblDescription,lblSubtotal,lblGrandTotal;
    EditText txtQty;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add_to_cart);
        //
        imgProduct = findViewById(R.id.imgProduct);
        lblProductName = findViewById(R.id.lblProductName);
        lblPrice = findViewById(R.id.lblPrice);
        lblDescription = findViewById(R.id.lblDescription);
        lblSubtotal = findViewById(R.id.lblSubtotal);
        lblGrandTotal = findViewById(R.id.lblGrandtotal);
        txtQty = findViewById(R.id.txtQty);
        //Set Product ListView config then populate it with items from the FireBase
        ProductView = findViewById(R.id.ProductView);
        ProductView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        setProductList();
        ArrayAdapter<String> ProductAdapter = new ArrayAdapter<>(CustomerAddToCart.this,R.layout.layout_product_view,R.id.checkedTextView,ProductList);
        ProductView.setAdapter(ProductAdapter);
        ProductView.setOnItemClickListener(this);
        //Set Listeners for each button in this activity
        setButtonListeners();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = ((TextView) view).getText().toString();
        if(SelectedItems.get(position).get("Name").contains(selectedItem))
        {
            SelectedItems.remove(selectedItem); //This will "Uncheck" the item
            Log.d("Selected Item List:",String.valueOf(SelectedItems));
        }
        else
        {
            lblProductName.setText(String.valueOf(ProductMap.get(position).get("Name")));
            lblPrice.setText(String.valueOf(ProductMap.get(position).get("Price")));
            lblDescription.setText(String.valueOf(ProductMap.get(position).get("Description")));
            storageRef = FirebaseStorage.getInstance().getReference("ProductUploads");
            
            final StorageReference dataRef = storageRef.child(String.valueOf(ProductMap.get(position).get("Image")));
            dataRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(imgProduct); // Using Picasso to adjust picture
                }
            });
            
            showDialog(ProductMap.get(position).get("Name"),"Add Quantity");
        }
    }
    
    protected void showDialog(final String ProductName, final String message){

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.updatedialogitem,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(promptView);
        builder.setTitle(message);

        final EditText txtQty = (EditText) promptView.findViewById(R.id.txtQty);

        builder.setCancelable(false).setPositiveButton("Add To Cart", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
                //Check if EditText is null -> showDialog again
                //Check if EditText is not null -> add productid, productname and quantity to the arraylist<HashMap<String,String>>

                if(txtQty.isEmpty() || Integer.parseInt(txtQty.getText().toString()) < 1 )
                    showDialog(ProductName,"Please Enter Valid Quantity Number");
                
            HashMap<String,String> ItemSelectedMap = new HashMap<>();
            //ItemSelectedMap.put("ID",String.valueOf(ProductMap.get(position).get("Name")));    
            ItemSelectedMap.put("Name",String.valueOf(ProductMap.get(position).get("Name")));
            ItemSelectedMap.put("Qty",String.valueOf(txtQty.getText()));
            SelectedItems.add(ItemSelectedMap);
          }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Uncheck the checkbox
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    
     // Creating Alert dialog for confirmation on back press //
    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to go back?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
    
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnCheckout: Checkout(); break;
            case R.id.btnBack: Back(); break;
        }
    }

    private void setProductList()
    {
        dbRef = FirebaseDatabase.getInstance().getReference().child("Product");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren())
                {

                    Log.d("Data Key",String.valueOf(data.getKey()));
                    Log.d("Data Product Name",String.valueOf(data.child("Name").getValue()));

                    product.setName(String.valueOf(data.child("Name").getValue()));
                    product.setCategory(String.valueOf(data.child("Category").getValue()));
                    product.setPrice(String.valueOf(data.child("Price").getValue()));
                    product.setDescription(String.valueOf(data.child("Description").getValue()));
                    product.setStatus(String.valueOf(data.child("Status").getValue()));
                    product.setImage(String.valueOf(data.child("Image").getValue()));

                    ProductList.add(product.getName());
                    ProductMap.add(product.getItemMap());
                }

                Log.d("Item HashMap >>", String.valueOf(product.getItemMap()));
                Log.d("Product List", String.valueOf(ProductList));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CustomerAddToCart.this, String.valueOf(databaseError.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Checkout()
    {
        Intent intent = new Intent(CustomerAddToCart.this,CustomerViewCart.class);
        intent.putExtra("Cart Map",SelectedItems);
        startActivity(intent);
        finish();
    }

    private void Back()
    {
        Intent intent = new Intent(CustomerAddToCart.this,CustomerPanel.class);
        startActivity(intent);
        finish();
    }

    private void setButtonListeners()
    {
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnCheckout).setOnClickListener(this);
        findViewById(R.id.btnAdd).setOnClickListener(this);
    }
}
