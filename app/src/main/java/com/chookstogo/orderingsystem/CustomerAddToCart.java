package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/*
* https://stackoverflow.com/questions/3004713/get-content-uri-from-file-path-in-android
* */
public class CustomerAddToCart extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener{

    DatabaseReference dbRef;
    StorageReference storageRef;
    ListView ProductView;
    ArrayList<String> ProductList = new ArrayList<>(); // List of Product Name that will be populated in listview
    ArrayList<Product> ProductMap = new ArrayList<>(); //All Product List will be displayed in the xml file
    ArrayList<Cart> CartList = new ArrayList<>(); //Sample Prototype
    ArrayList<String> SelectedItems = new ArrayList<>(); //Sample Prototype
    ImageView imgProduct;
    TextView lblProductName,lblPrice,lblDescription,lblSubtotal,lblGrandTotal;
    EditText txtQty;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add_to_cart);

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
        setButtonListeners();
        ArrayAdapter<String> ProductAdapter = new ArrayAdapter<>(CustomerAddToCart.this,R.layout.layout_product_view,R.id.checkedTextView,ProductList);
        ProductView.setAdapter(ProductAdapter);
        ProductView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = ((TextView) view).getText().toString();

        if(SelectedItems.contains(selectedItem))
        {
            SelectedItems.remove(selectedItem);
            Log.d("Item Removed >>",String.valueOf(SelectedItems));

            for (Cart cart: CartList) {
                if(cart.getName().equals(selectedItem))
                {
                    Log.d("Cart Name",String.valueOf(cart.getName()));
                    CartList.remove(cart);
                    Log.d("Cart List Removed >>",String.valueOf(CartList));
                    break;
                }
                else
                {
                    continue;
                }
            }
        }
        else
        {
            lblProductName.setText(String.valueOf(ProductMap.get(position).getName()));
            lblPrice.setText(String.valueOf(ProductMap.get(position).getPrice()));
            lblDescription.setText(String.valueOf(ProductMap.get(position).getDescription()));
            storageRef = FirebaseStorage.getInstance().getReference("ProductUploads");

            final StorageReference dataRef = storageRef.child(String.valueOf(ProductMap.get(position).getImage()));
            dataRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(imgProduct); // Using Picasso to adjust picture
                }
            });
            //OpenDialog for Quantity
            String message = "Add Quantity to " + selectedItem;
            showDialog(message,position);
        }
    }

    protected void showDialog(final String message, final int position){

        LayoutInflater layoutInflater = LayoutInflater.from(CustomerAddToCart.this);
        View promptView = layoutInflater.inflate(R.layout.layout_qty_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerAddToCart.this);
        builder.setView(promptView);
        builder.setTitle(message);

        final EditText txtQty = promptView.findViewById(R.id.txtQty);

        builder.setCancelable(false).setPositiveButton("Add To Cart", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {

                if(txtQty.getText().toString().isEmpty() || Integer.parseInt(txtQty.getText().toString()) < 1 )
                    showDialog(message,position);
                //Add it to CartList
              Cart cart = new Cart();
              cart.setName(String.valueOf(ProductMap.get(position).getName()));
              cart.setQty(String.valueOf(txtQty.getText().toString()));
              CartList.add(cart);
              SelectedItems.add(cart.getName());
              Log.d("Selected Items >>",String.valueOf(SelectedItems));
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

     // Creating Alert dialog for confirmation on back press
    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to go back?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CustomerAddToCart.super.onBackPressed();
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
                    Product product = new Product();
                    product.setID(String.valueOf(data.getKey()));
                    product.setName(String.valueOf(data.child("Name").getValue()));
                    product.setCategory(String.valueOf(data.child("Category").getValue()));
                    product.setPrice(String.valueOf(data.child("Price").getValue()));
                    product.setDescription(String.valueOf(data.child("Description").getValue()));
                    product.setStatus(String.valueOf(data.child("Status").getValue()));
                    product.setImage(String.valueOf(data.child("Image").getValue()));

                    ProductList.add(product.getName());
                    ProductMap.add(product);
                }
                Log.d("Item HashMap >>", String.valueOf(ProductMap));
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
        //Check out means ORDER NOW
        String time = String.valueOf(System.currentTimeMillis());
        SendToOrder_User(time);
    }


    private void SendToOrder_User(final String time)
    {
        //Get All Credentials of User in UserMap
        Intent b = getIntent();
        HashMap<String,String> UserMap = (HashMap<String,String>) b.getSerializableExtra("UserMap"); // This contains ALL of USER CREDENTIALS
        Log.d("Customer Cart UserMap",String.valueOf(UserMap));
        Log.d("UserMap First Name",String.valueOf(UserMap.get("Fname")));
        Log.d("UserMap Last Name",String.valueOf(UserMap.get("Lname")));

        dbRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(time).child("UserID");
        dbRef.push().setValue(UserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    // progbarProduct.setVisibility(View.GONE);
                    Toast.makeText(CustomerAddToCart.this, "Upload Product on Database Success!\n Please wait for the image to upload", Toast.LENGTH_SHORT).show();
                    SendToOrder_Product(time);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //progbarProduct.setVisibility(View.GONE);
                Toast.makeText(CustomerAddToCart.this, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SendToOrder_Product(final String time)
    {
        dbRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(time).child("Product");
        dbRef.push().setValue(CartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    // progbarProduct.setVisibility(View.GONE);
                    Toast.makeText(CustomerAddToCart.this, "Ordered Product Successfully Uploaded!\n Please wait for the Details to be uploaded...", Toast.LENGTH_LONG).show();
                    SendToOrder_Details(time);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // progbarProduct.setVisibility(View.GONE);
                Toast.makeText(CustomerAddToCart.this, String.valueOf(e.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SendToOrder_Details(String time)
    {
        HashMap<String,String> DetailsMap = new HashMap<>();
        //Get Current Date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate =  mdformat.format(calendar.getTime());

        DetailsMap.put("OrderDate",String.valueOf(strDate));
        DetailsMap.put("Status","Pending");

        dbRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(time).child("Details");
        dbRef.push().setValue(DetailsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    // progbarProduct.setVisibility(View.GONE);
                    Toast.makeText(CustomerAddToCart.this, "Order Details Successfully Sent!", Toast.LENGTH_SHORT).show();
                    GoToCustomerOrderHistory();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // progbarProduct.setVisibility(View.GONE);
                Toast.makeText(CustomerAddToCart.this, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GoToCustomerOrderHistory()
    {
        Intent intent = new Intent(CustomerAddToCart.this,CustomerOrderHistory.class);
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
    }
}
