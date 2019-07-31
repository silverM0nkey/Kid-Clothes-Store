package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.ProductView;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.models.SizeModel;
import com.happybaby.kcs.restapi.gooco.responses.ResponseSize;
import com.happybaby.kcs.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductPresenter extends BasePresenter{

    private ProductView productView;
    private boolean fromAdd;
    private ResponseSize currentSize;
    private List<SizeModel> sizesModel;
    private String storeId;
    private String selectedItem;
    private String productName;
    private Integer originalPrice;
    private Integer finalPrice;
    private String urlImage;
    private String currency;
    private ArrayList<ResponseSize> sizes;

    public ProductPresenter(ProductView productView, String storeId, String selectedItem, String productName, Integer originalPrice, Integer finalPrice, String urlImage, String currency, ArrayList<ResponseSize> sizes) {
        super();
        this.productView = productView;
        this.storeId = storeId;
        this.selectedItem = selectedItem;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.urlImage = urlImage;
        this.currency = currency;
        this.sizes = sizes;
    }

    private void addProductToShoppingCart(String storeId, String modelId, String name, ResponseSize currentSize, Integer finalPrice, String urlImage, String currency) {
        if (this.productView != null) {
            ShoppingCartProduct shoppingCart = shoppingCartInteractor.getCurrentUserProductByIds(modelId, currentSize.getVariantId());
            if (shoppingCart != null) {
                shoppingCart.setQty(shoppingCart.getQty() + 1);
                shoppingCart.setFinalPrice(finalPrice);
            } else {
                shoppingCart = new ShoppingCartProduct(CustomerProfile.getCustomerProfile().getEmail(), storeId,
                        modelId, currentSize.getVariantId(), name, finalPrice, currentSize.getName(), urlImage, 1, currency);
            }
            shoppingCartInteractor.addProductByCurrentUser(shoppingCart);
            productView.updateCartIcon(true);
        }
    }

    public boolean isAddButtonEnabled(){
        return this.sizes.stream().filter(s -> s.getStockQty() > 0).findFirst().orElse(null) != null;
    }

    public void selectSize(int sizeSelected) {
        if (sizesModel.get(sizeSelected).getStockQty() > 0) {
            currentSize = sizesModel.get(sizeSelected).toResponseSize();
            if (fromAdd) {
                addProductToShoppingCart(storeId, selectedItem, productName, currentSize, finalPrice, urlImage, currency);
            }
            if (this.productView != null) {
                productView.selectionFinished(currentSize);
            }
        }
    }

    public void setPrices() {
        if (this.productView != null) {
            productView.setFinalPrice(String.format("%s %s", Util.getSymbol(currency), Float.valueOf(finalPrice.floatValue() / 100).toString()));
        }
        if (originalPrice !=  null &&  !originalPrice.equals(finalPrice)) {
            if (this.productView != null) {
                productView.setOriginalPrice(String.format("%s %s", Util.getSymbol(currency), Float.valueOf(originalPrice.floatValue() / 100).toString()));
            }
        }
    }

    public void onClickShoppingCart() {
        if (shoppingCartInteractor.countAllCurrentUserProducts() > 0) {
            productView.goToShoppingCart();
        } else {
            productView.showEmptyCartMessage();
        }
    }

    public void addProduct() {
        if (currentSize == null) {
            if (this.productView != null) {
                productView.showSizePopup(true);
            }
        } else {
            addProductToShoppingCart(storeId, selectedItem, productName, currentSize, finalPrice, urlImage, currency);
        }
    }

    public void loadSizeModel(boolean fromAdd) {
        this.fromAdd = fromAdd;
        sizesModel = sizes
                .stream().map(rs -> new SizeModel(rs.getVariantId(), rs.getName(), rs.getStockQty(), rs.getVariantId().equals(this.currentSize != null ? this.currentSize.getVariantId() : null))).
                        collect(Collectors.toList());
        if (this.productView != null) {
            productView.loadSizeModelFinished(sizesModel);
        }
    }

    public void unbindView(){
        this.productView = null;
    }

}
