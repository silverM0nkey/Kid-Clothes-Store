package com.happybaby.kcs.models.interactors;

import android.content.Context;

import com.happybaby.kcs.bd.room.AppDatabase;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.happybaby.kcs.dagger.App;
import com.happybaby.kcs.models.CustomerProfile;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ShoppingCartInteractor {

    @Inject
    Context context;

    public ShoppingCartInteractor() {
        App.getAppComponent().inject(this);
    }

    public void changeQytToCurrentUserProduct(String modelId, String variantId, int qty) {
        if (context != null) {
            ShoppingCartProduct shoppingCart = AppDatabase.getInstance(context).
                    shoppingCartDao().findProductsByCustomerAndIds(CustomerProfile.getCustomerProfile().getEmail(), modelId, variantId);
            if (shoppingCart != null) {
                shoppingCart.setQty(qty);
                AppDatabase.getInstance(context).shoppingCartDao().insertProducts(shoppingCart);
            }
        }
    }

    public List<ShoppingCartProduct> getCurrentUserProducts() {
        if (context != null) {
            List<ShoppingCartProduct> result = AppDatabase.getInstance(this.context).shoppingCartDao().
                    findProductsByCustomer(CustomerProfile.getCustomerProfile().getEmail());
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    public void deleteCurrentUserProduct(String modelId, String variantId) {
        if (context != null) {
            AppDatabase.getInstance(context).shoppingCartDao().
                    deleteProductsByCustomerAndIds(CustomerProfile.getCustomerProfile().getEmail(), modelId, variantId);
        }
    }

    public void deleteAllCurrentUserProducts() {
        if (context != null) {
            AppDatabase.getInstance(context).shoppingCartDao().deleteProductsByCustomer(CustomerProfile.getCustomerProfile().getEmail());
        }
    }

    public Integer countAllCurrentUserProducts() {
        if (context != null) {
            return AppDatabase.getInstance(context).shoppingCartDao().countProductsByCustomer(
                    CustomerProfile.getCustomerProfile().getEmail());
        } else {
            return 0;
        }
    }

    public void addProductByCurrentUser(ShoppingCartProduct shoppingCart) {
        if (context != null) {
            AppDatabase.getInstance(context).shoppingCartDao().insertProducts(shoppingCart);
        }
    }

    public ShoppingCartProduct getCurrentUserProductByIds(String modelId, String variantId) {
        if (context != null) {
            return AppDatabase.getInstance(context).
                    shoppingCartDao().findProductsByCustomerAndIds(CustomerProfile.getCustomerProfile().
                    getEmail(), modelId, variantId);
        } else {
            return null;
        }
    }

    public void changeShoppingCartInLogin(String email) {
        if (context != null) {
            List<ShoppingCartProduct> productList = AppDatabase.
                    getInstance(context).shoppingCartDao().findProductsByCustomer(CustomerProfile.CUSTOMER_ANONYMOUS);

            for (ShoppingCartProduct product : productList) {
                product.setCustomer(email);
            }
            AppDatabase.getInstance(context).shoppingCartDao().insertAll(productList);
            AppDatabase.getInstance(context).shoppingCartDao().deleteProductsByCustomer(CustomerProfile.CUSTOMER_ANONYMOUS);
        }
    }
}
