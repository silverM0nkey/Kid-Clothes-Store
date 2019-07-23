package com.happybaby.kcs.bd.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "shopping_cart", primaryKeys = {"customer", "model_id", "variant_id"})
public class ShoppingCartProduct {
    @ColumnInfo(name = "customer")
    @NonNull
    private String customer;

    @ColumnInfo(name = "store_id")
    private String storeId;

    @ColumnInfo(name = "model_id")
    @NonNull
    private String modelId;

    @ColumnInfo(name = "variant_id")
    @NonNull
    private String variantId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "final_price")
    private Integer finalPrice;

    @ColumnInfo(name = "size")
    private String size;

    @ColumnInfo(name = "url_image")
    private String urlImage;

    @ColumnInfo(name = "qty")
    private Integer qty;

    @ColumnInfo(name = "currency")
    private String currency;

    public ShoppingCartProduct(@NonNull String customer, String storeId, @NonNull String modelId, @NonNull String variantId, String name, Integer finalPrice, String size, String urlImage, Integer qty, String currency) {
        this.customer = customer;
        this.storeId = storeId;
        this.modelId = modelId;
        this.variantId = variantId;
        this.name = name;
        this.finalPrice = finalPrice;
        this.urlImage = urlImage;
        this.size = size;
        this.qty = qty;
        this.currency = currency;
    }

    @NonNull
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(@NonNull String customer) {
        this.customer = customer;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @NonNull
    public String getModelId() {
        return modelId;
    }

    public void setModelId(@NonNull String modelId) {
        this.modelId = modelId;
    }

    @NonNull
    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(@NonNull String variantId) {
        this.variantId = variantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCurrency() { return currency; }

    public void setCurrency(String currency) { this.currency = currency; }
}