package com.chookstogo.orderingsystem;

import android.net.Uri;

import java.util.HashMap;

public class Product {
    private String Name;
    private String Price;
    private String Category;
    private String Description;
    private String Image;
    private String Status;


    public Product()
    {
        setName(null);
        setCategory(null);
        setPrice(null);
        setDescription(null);
        setImage(null);
        setStatus(null);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public HashMap<String,String> getItemMap()
    {
        HashMap<String,String> itemList = new HashMap<>();
        itemList.put("Name",getName());
        itemList.put("Category",getCategory());
        itemList.put("Price",getPrice());
        itemList.put("Description",getDescription());
        itemList.put("Image",getImage());
        itemList.put("Status",getStatus());
        return itemList;
    }
}
