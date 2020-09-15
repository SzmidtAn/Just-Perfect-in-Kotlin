package com.example.girlsshopping.products;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM product")
    List<Product> findAll();

    @Insert
    void insert(Product product);

    @Query("SELECT * FROM product WHERE id=:id")
    Product findById(long id);


    @Query("SELECT * FROM product WHERE favourite= 'true'")
    List<Product> findFavourites();

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);


}
