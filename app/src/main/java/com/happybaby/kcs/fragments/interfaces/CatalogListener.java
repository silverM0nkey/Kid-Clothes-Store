package com.happybaby.kcs.fragments.interfaces;

public interface CatalogListener {
    void onChangeCategory(String categoryId, String categoryName);
    void onShowProduct(String modelId);
}
