package com.happybaby.kcs.bd.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ShoppingCartDao {
    @Query("SELECT * FROM shopping_cart")
    List<ShoppingCartProduct> getAll();

    @Query("SELECT * FROM shopping_cart WHERE customer LIKE :customer")
    List<ShoppingCartProduct> findProductsByCustomer(String customer);

    @Query("SELECT * FROM shopping_cart WHERE customer LIKE :customer and model_id LIKE :modelId and variant_id LIKE :variantId")
    ShoppingCartProduct findProductsByCustomerAndIds(String customer, String modelId, String variantId);

    @Query("DELETE FROM shopping_cart")
    void deleteAll();

    @Query("DELETE FROM shopping_cart WHERE customer LIKE :customer")
    void deleteProductsByCustomer(String customer);

    @Query("DELETE FROM shopping_cart WHERE customer LIKE :customer and model_id LIKE :modelId and variant_id LIKE :variantId")
    void deleteProductsByCustomerAndIds(String customer, String modelId, String variantId);

    @Insert(onConflict = REPLACE)
    void insertProducts(ShoppingCartProduct... shoppingCarts);

    @Insert(onConflict = REPLACE)
    void insertAll(List<ShoppingCartProduct> shoppingCarts);

    @Delete
    void delete(ShoppingCartProduct shoppingCart);

    @Query("SELECT COUNT(*) FROM shopping_cart WHERE customer LIKE :customer")
    int countProductsByCustomer(String customer);

}