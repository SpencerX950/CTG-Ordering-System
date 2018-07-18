package com.chookstogo.orderingsystem;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class User {
    private String Lname;
    private String Fname;
    private String Email;
    private String Mobile;
    private String Username;
    private String Pass;
    private String Type;
    private boolean success;
    private DatabaseReference fireAuth;

    public User()
    {
        setFname("");
        setLname("");
        setEmail("");
        setPass("");
        setType("");
        setMobile("");
        setUsername("");
        setSuccess(false);
    }

    public String getLname() {
        return Lname;
    }
    public void setLname(String lname) {
        Lname = lname;
    }
    public String getFname() {
        return Fname;
    }
    public void setFname(String fname) {
        Fname = fname;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getMobile() {
        return Mobile;
    }
    public void setMobile(String mobile) {
        Mobile = mobile;
    }
    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public String getPass() {
        return Pass;
    }
    public void setPass(String pass) {
        Pass = pass;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HashMap<String, String> getUserMap() {
        HashMap<String,String> UserMap = new HashMap<>();
        UserMap.put("Fname",Fname);
        UserMap.put("Lname",Lname);
        UserMap.put("Mobile",Mobile);
        UserMap.put("Email",Email);
        UserMap.put("Username",Username);
        UserMap.put("Password",Pass);
        UserMap.put("UserType",Type);
        return UserMap;
    }

    public boolean RegisterUser()
    {
        fireAuth = FirebaseDatabase.getInstance().getReference("User");
        fireAuth.push().setValue(getUserMap()).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful()){
                    setSuccess(true);
                }
                else
                {
                    setSuccess(false);
                }
            }
        });
        return isSuccess();
    }

}
