package com.chookstogo.orderingsystem;

import java.util.HashMap;

public class Cart {
    private String Name;
    private String Qty;

    public Cart()
    {
        setName("");
        setQty("");
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

}
