package com.chookstogo.orderingsystem;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class User {
    private String ID;
    private String Lname;
    private String Fname;
    private String Email;
    private String Mobile;
    private String Username;
    private String Pass;
    private String Type;


    public User()
    {
        setID("");
        setFname("");
        setLname("");
        setEmail("");
        setPass("");
        setType("");
        setMobile("");
        setUsername("");

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public HashMap<String, String> getUserMap() {
        HashMap<String,String> UserMap = new HashMap<>();
        UserMap.put("ID",ID);
        UserMap.put("Fname",getFname());
        UserMap.put("Lname",getLname());
        UserMap.put("Mobile",getMobile());
        UserMap.put("Email",getEmail());
        UserMap.put("Username",getUsername());
        UserMap.put("Password",getPass());
        UserMap.put("Type",getType());
        return UserMap;
    }

    public HashMap<String,String> getUserOrderMap()
    {
        HashMap<String,String> UserMap = new HashMap<>();
        UserMap.put("ID",ID);
        UserMap.put("Fname",Fname);
        UserMap.put("Lname",Lname);
        return UserMap;
    }
}
