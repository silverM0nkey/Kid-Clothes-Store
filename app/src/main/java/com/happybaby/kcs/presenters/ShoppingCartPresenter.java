package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.ShoppingCartListener;
import com.happybaby.kcs.activities.interfaces.ShoppingCartView;
import com.happybaby.kcs.bd.room.AppDatabase;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.utils.Util;

import java.util.List;

public class ShoppingCartPresenter implements ShoppingCartListener {

    private ShoppingCartView shoppingCartView;
    private List<ShoppingCartProduct> shoppingCartList;

    public ShoppingCartPresenter(ShoppingCartView shoppingCartView) {
        this.shoppingCartView =  shoppingCartView;
    }

    public void updateTotalPrice() {
        float totalPrice = 0;
        String currency = "";
        for (ShoppingCartProduct shoppingCart: this.shoppingCartList) {
            currency = shoppingCart.getCurrency();
            totalPrice = totalPrice + shoppingCart.getFinalPrice().floatValue() / 100 * shoppingCart.getQty();
        }
        this.shoppingCartView.setTotalProducts(String.format("%s %.2f", Util.getSymbol(currency), totalPrice));
        this.shoppingCartView.setTotal(String.format("%s %.2f",  Util.getSymbol(currency), totalPrice));
    }


    public void onRemoveProduct(String modelId, String variantId) {
        ShoppingCartProduct productToRemove = this.shoppingCartList.stream().filter(p -> p.getModelId().equals(modelId) && p.getVariantId().equals(variantId)).findFirst().orElse(null);
        if (productToRemove != null) {
            this.shoppingCartList.remove(productToRemove);
            this.shoppingCartView.notifyDataSetChanged();
            updateTotalPrice();
            if (this.shoppingCartList.size() == 0) {
                this.shoppingCartView.confirmPurchaseEnabled(false);
            }
            deleteProductInModel(modelId, variantId);
        }
    }

    public void onChangeQyt(String modelId, String variantId, int qty){
        ShoppingCartProduct productToModify = this.shoppingCartList.stream().filter(p -> p.getModelId().equals(modelId) && p.getVariantId().equals(variantId)).findFirst().orElse(null);
        if (productToModify != null) {
            if (productToModify.getQty() < qty) {
                productToModify.setQty(qty);
                updateTotalPrice();
            } else {
                productToModify.setQty(qty);
                updateTotalPrice();
            }
            if (shoppingCartView != null) {
                this.shoppingCartView.notifyDataSetChanged();
            }
        }
        changeQytInModel(modelId, variantId, qty);
    }

    public void confirmPurchase(){
        if (shoppingCartView != null) {
            if (CustomerProfile.getCustomerProfile().getEmail().equals(CustomerProfile.CUSTOMER_ANONYMOUS)) {
                shoppingCartView.startLoginActivity();
            } else {
                AppDatabase.getInstance(this.shoppingCartView.getContext()).shoppingCartDao().deleteProductsByCustomer(CustomerProfile.getCustomerProfile().getEmail());
                shoppingCartView.showPurchaseMessage();
            }
        }
    }

    public void loadShoppingCart() {
        this.shoppingCartList = AppDatabase.getInstance(this.shoppingCartView.getContext()).
                shoppingCartDao().findProductsByCustomer(CustomerProfile.getCustomerProfile().getEmail());
        if (shoppingCartView != null) {
            shoppingCartView.loadFinished(this.shoppingCartList);
        }
    };

    private void changeQytInModel(String modelId, String variantId, int qty) {
        if (shoppingCartView != null) {
            ShoppingCartProduct shoppingCart = AppDatabase.getInstance(this.shoppingCartView.getContext()).
                    shoppingCartDao().findProductsByCustomerAndIds(CustomerProfile.getCustomerProfile().getEmail(), modelId, variantId);
            if (shoppingCart != null) {
                shoppingCart.setQty(qty);
                AppDatabase.getInstance(this.shoppingCartView.getContext()).shoppingCartDao().
                        insertProducts(shoppingCart);
            }
        }
    }

    private void deleteProductInModel(String modelId, String variantId) {
        if (shoppingCartView != null) {
            AppDatabase.getInstance(this.shoppingCartView.getContext()).shoppingCartDao().
                    deleteProductsByCustomerAndIds(CustomerProfile.getCustomerProfile().getEmail(), modelId, variantId);
        }
    }

    public void unbindView(){
        this.shoppingCartView = null;
    }

}