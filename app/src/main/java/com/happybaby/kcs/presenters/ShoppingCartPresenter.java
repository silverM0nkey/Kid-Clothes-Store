package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.ShoppingCartListener;
import com.happybaby.kcs.activities.interfaces.ShoppingCartView;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.utils.Util;

import java.util.List;

public class ShoppingCartPresenter extends BasePresenter implements ShoppingCartListener {

    private ShoppingCartView shoppingCartView;
    private List<ShoppingCartProduct> shoppingCartList;

    public ShoppingCartPresenter(ShoppingCartView shoppingCartView) {
        super(shoppingCartView.getContext());
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
            this.shoppingCartInteractor.deleteCurrentUserProduct(modelId, variantId);
        }
    }

    public void onChangeQyt(String modelId, String variantId, int qty){
        ShoppingCartProduct productToModify = this.shoppingCartList.stream().filter(p -> p.getModelId().equals(modelId) && p.getVariantId().equals(variantId)).findFirst().orElse(null);
        if (productToModify != null) {
            productToModify.setQty(qty);
            updateTotalPrice();

            if (shoppingCartView != null) {
                this.shoppingCartView.notifyDataSetChanged();
            }
        }
        this.shoppingCartInteractor.changeQytToCurrentUserProduct(modelId, variantId, qty);
    }

    public void confirmPurchase(){
        if (shoppingCartView != null) {
            if (CustomerProfile.getCustomerProfile().getEmail().equals(CustomerProfile.CUSTOMER_ANONYMOUS)) {
                shoppingCartView.startLoginActivity();
            } else {
                shoppingCartInteractor.deleteAllCurrentUserProducts();
                shoppingCartView.showPurchaseMessage();
            }
        }
    }

    public void loadShoppingCart() {
        this.shoppingCartList = shoppingCartInteractor.getCurrentUserProducts();
        if (shoppingCartView != null) {
            shoppingCartView.loadFinished(this.shoppingCartList);
        }
    }

    public void unbindView(){
        this.shoppingCartView = null;
    }

}