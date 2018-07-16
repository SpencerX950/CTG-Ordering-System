package com.chookstogo.orderingsystem;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserAuthentication{
    private User user;
    protected DatabaseReference fireauth;


    public void setNewUser(User user)
    {
        //Database Init
        //fireauth = FirebaseDatabase.getInstance().getReference();
        fireauth.push().setValue(user.getUserMap()).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful()){
                    //do something
                }
                else
                {
                    //do something
                }
            }
        });
    }


    public User getNewUser() {
        return user;
    }
}
