package com.example.girlsshopping.products;

public enum ProductCategory {

    CLOTHES("Ubrania"), ACCESSORIES("Akcesoria"), SHOES("Buty"), OTHER("Inne");

    private String name;

    ProductCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
