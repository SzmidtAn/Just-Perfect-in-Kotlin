package com.example.girlsshopping.products;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String description;
    private String  price;
    private String photoString;
    private String size;
    private String brand;
    private boolean favourite;

    public boolean isFavourite() {
        return favourite;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @TypeConverters(ProductCategoryConverter.class)
    private ProductCategory category;

    public String getSize() {
        return size;
    }

    public String getBrand() {
        return brand;
    }

    public String getCondition() {
        return condition;
    }

    private String condition;

@SuppressWarnings("unused")
    public Product(String name, String description, ProductCategory category, String price, String photoString, String size, String brand, String condition) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.photoString = photoString;
        this.size = size;
        this.brand = brand;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhotoString() {
        return photoString;
    }
}