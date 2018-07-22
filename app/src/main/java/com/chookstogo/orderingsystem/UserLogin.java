package com.chookstogo.orderingsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserLogin extends AppCompatActivity implements View.OnClickListener{
    User user = new User();
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    EditText txtEmail,txtPassword;
    ProgressBar progbarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        progbarLogin = findViewById(R.id.progbarLogin);
        findViewById(R.id.lblSignIn).setOnClickListener(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        //Set progressbar invisible
        progbarLogin.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.lblSignIn: GoToRegisterActivity(); break;
            case R.id.btnLogin: CheckLoginCredentials(); break;
        }
    }

    private void CheckLoginCredentials()
    {
        progbarLogin.setVisibility(View.VISIBLE);

        final String email = txtEmail.getText().toString().trim();
        final String pass = txtPassword.getText().toString().trim();

        if(email.isEmpty())
            MissingFields(txtEmail);
        //Check if input email is Authentic Email
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            InvalidEmailPattern();
        if(pass.isEmpty())
            MissingFields(txtPassword);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progbarLogin.setVisibility(View.GONE);
                    Log.d("User ID >>",String.valueOf(task.getResult().getUser().getUid()));
                    UserTypeValidation(String.valueOf(task.getResult().getUser().getUid()));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progbarLogin.setVisibility(View.GONE);
                Toast.makeText(UserLogin.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    //Check if Customer or Admin
    private void UserTypeValidation(final String UserID)
    {
        user.setEmail(txtEmail.getText().toString());
        user.setPass(txtPassword.getText().toString());
        user.setID(UserID);

        dbRef = FirebaseDatabase.getInstance().getReference().child("User").child(UserID).child("Roles");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    Log.d(" User Type >>", String.valueOf(data.getValue()));
                    user.setType(String.valueOf(data.getValue()));
                }

                Log.d("User HashMap >>", String.valueOf(user.getUserMap()));

                switch(user.getType())
                {
                    case "Customer": GoToCustomerActivity(); break;
                    case "Admin": GoToAdminActivity(); break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserLogin.this, String.valueOf(databaseError.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GoToAdminActivity()
    {
        Intent intent = new Intent(UserLogin.this,AdminPanel.class);
        intent.putExtra("User Map",user.getUserMap());
        startActivity(intent);
        finish();
    }
    private void GoToCustomerActivity()
    {
        Intent intent = new Intent(UserLogin.this,CustomerPanel.class);
        intent.putExtra("User Map",user.getUserMap());
        startActivity(intent);
        finish();
    }

    private void GoToRegisterActivity()
    {
        Intent intent = new Intent(UserLogin.this,UserRegister.class);
        startActivity(intent);
        finish();
    }

    private void MissingFields(EditText field)
    {
        field.setError("This Field is Required");
        field.requestFocus();
    }

    private void InvalidEmailPattern()
    {
        txtEmail.setError("Please Enter Valid Email");
        txtEmail.requestFocus();
    }
}
