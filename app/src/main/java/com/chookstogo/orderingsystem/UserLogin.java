package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserLogin extends AppCompatActivity{

    TextView lblSignup;
    public User user = new User();
    EditText txtEmail,txtPassword;
    DatabaseReference fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        //set all required fields to connect
        lblSignup = findViewById(R.id.lblSignup);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        // call method to change textview for signup
        CreateClickableSignup();
    }

    public void LoginUser(View v)
    {
        try{
                Log.d("Login Constraint","Email and Pass are NOT EMPTY!");
                fireAuth = FirebaseDatabase.getInstance().getReference().child("User");
                fireAuth.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){

                            if(String.valueOf(txtEmail.getText().toString()).equals(String.valueOf(data.child("Email").getValue())) && String.valueOf(txtPassword.getText().toString()).equals(String.valueOf(data.child("Password").getValue())))
                            {
                                user.setEmail(String.valueOf(data.child("Email").getValue()));
                                user.setPass(String.valueOf(data.child("Password").getValue()));
                                user.setFname(String.valueOf(data.child("Fname").getValue()));
                                user.setLname(String.valueOf(data.child("Lname").getValue()));
                                user.setMobile(String.valueOf(data.child("Mobile").getValue()));
                                user.setUsername(String.valueOf(data.child("Username").getValue()));
                                user.setType(String.valueOf(data.child("Type").getValue()));
                                Log.d("User Map: ",String.valueOf(user.getUserMap()));
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        LoginError(databaseError);
                    }
                });
        }
        catch(NullPointerException e)
        {
            ErrorEmptyFields(e);
        }

        //After checking if valid user and getting credentials
        UserValidation();
    }

    public void CreateClickableSignup()
    {
        //Create Clickable "Sign up" text view
        SpannableString signUp = new SpannableString("Don\'t have an Account? Sign up now!");
        Log.d("Signup:", lblSignup.getText().toString());

        ClickableSpan clickSignup = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(UserLogin.this,UserRegister.class );
                startActivity(intent);
                finish();
            }
        };
        signUp.setSpan(clickSignup,23,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        lblSignup.setText(signUp);
        lblSignup.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void LoginError(DatabaseError databaseError)
    {
        Log.d("Login Database Error",String.valueOf(databaseError));
        Toast.makeText(UserLogin.this,"Login Failed",Toast.LENGTH_LONG).show();
    }

    public void ErrorEmptyFields(NullPointerException e)
    {
        Toast.makeText(UserLogin.this,"EMAIL AND PASS FIELDS ARE EMPTY...",Toast.LENGTH_LONG).show();
        Log.d("Empty Field Error >>",String.valueOf(e));
    }

    public void UserNotFound()
    {
        Toast.makeText(UserLogin.this,"ERROR 404: USER NOT FOUND...\n PLEASE DOUBLE CHECK THE CREDENTIALS",Toast.LENGTH_LONG).show();
        Log.d("IS USER FOUND?","ERROR 404: USER NOT FOUND...");
    }

    public void UserValidation()
    {
        switch (user.getType())
        {
            case "Customer": CustomerDestinationView(); break;
            case "Admin": AdminDestinationView(); break;
            default: UserNotFound(); break;
        }
    }

    public void AdminDestinationView()
    {
        Intent intent = new Intent(UserLogin.this,AdminPanel.class);
        intent.putExtra("User",user.getUserMap());
        startActivity(intent);
        finish();
    }

    public void CustomerDestinationView()
    {
        //Can be changed to Customer Order
        Intent intent = new Intent(UserLogin.this,CustomerProfile.class);
        intent.putExtra("User",user.getUserMap());
        startActivity(intent);
        finish();
    }
}
