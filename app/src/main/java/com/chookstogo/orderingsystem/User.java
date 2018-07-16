package com.chookstogo.orderingsystem;

import java.util.HashMap;

public class User {
    private String Lname;
    private String Fname;
    private String Email;
    private String Mobile;
    private String Username;
    private String Pass;

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

    public HashMap<String, String> getUserMap() {
        HashMap<String,String> UserMap = new HashMap<String,String>();
        UserMap.put("Fname",Fname);
        UserMap.put("Lname",Lname);
        UserMap.put("Mobile",Mobile);
        UserMap.put("Email",Email);
        UserMap.put("Username",Username);
        UserMap.put("Password",Pass);

        return UserMap;
    }



}
