package com.example.girlsshopping.products;

import androidx.room.TypeConverter;

public class ProductCategoryConverter {


    @TypeConverter
    public static ProductCategory toCategory(String name){
        return ProductCategory.valueOf(name);
    }

    @TypeConverter
    public String toString(ProductCategory category) {
        return category.name();
    }
}
