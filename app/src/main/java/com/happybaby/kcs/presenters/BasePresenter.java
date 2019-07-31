package com.happybaby.kcs.presenters;

import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.happybaby.kcs.models.interactors.ShoppingCartInteractor;

import java.util.List;

public class BasePresenter {

    protected ShoppingCartInteractor shoppingCartInteractor;

    public BasePresenter() {
        this.shoppingCartInteractor = new ShoppingCartInteractor();
    }

    public Integer getNumberOfProducts() {
        List<ShoppingCartProduct> products = shoppingCartInteractor.getCurrentUserProducts();
        Integer totalNumberOfProducts = 0;
        for (ShoppingCartProduct product: products){
            totalNumberOfProducts = totalNumberOfProducts + product.getQty();
        }
        return totalNumberOfProducts;
    }
}
