package com.example.girlsshopping.products;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductsDataBase extends RoomDatabase {

    public abstract ProductDao getProductDao();

    private static volatile ProductsDataBase INSTANCE;

    public static ProductsDataBase getDataBase(final Context context){
        if (INSTANCE == null) {
            synchronized (ProductsDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductsDataBase.class, "girlsShopping-db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}