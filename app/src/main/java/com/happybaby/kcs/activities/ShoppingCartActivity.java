package com.happybaby.kcs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.activities.interfaces.ShoppingCartListener;
import com.happybaby.kcs.adapters.ShoppingCartRecyclerListAdapter;
import com.happybaby.kcs.bd.room.AppDatabase;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.utils.Util;

import java.util.List;


public class ShoppingCartActivity extends BaseActivity implements ShoppingCartListener {

    private ShoppingCartRecyclerListAdapter mShoppingCartRecyclerListAdapter;

    private TextView totalProducts;
    private TextView total;
    private Button confirmPurchase;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        setupToolbar();
        setTitle(getResources().getString(R.string.shopping_cart_tittle));

        RecyclerView mShoppingCartRecyclerView = findViewById(R.id.products);

        confirmPurchase = findViewById(R.id.accept);
        confirmPurchase.setOnClickListener(View -> {
                confirmPurchase();
        });

        List<ShoppingCartProduct> shoppingCartList = AppDatabase.getInstance(this).shoppingCartDao().findProductsByCustomer(CustomerProfile.getCustomerProfile().getEmail());
        mShoppingCartRecyclerListAdapter = new ShoppingCartRecyclerListAdapter(this, this, shoppingCartList);
        mShoppingCartRecyclerView.setAdapter(mShoppingCartRecyclerListAdapter);
        mShoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.recycler_view_divider));
        mShoppingCartRecyclerView.addItemDecoration(itemDecorator);

        totalProducts = findViewById(R.id.total_products);
        total = findViewById(R.id.total);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        float totalPrice = 0;
        String currency = "";
        for (ShoppingCartProduct shoppingCart: mShoppingCartRecyclerListAdapter.getItems()) {
            currency = shoppingCart.getCurrency();
            totalPrice = totalPrice + shoppingCart.getFinalPrice().floatValue() / 100 * shoppingCart.getQty();
        }
        totalProducts.setText(String.format("%s %.2f", Util.getSymbol(currency), totalPrice));
        total.setText(String.format("%s %.2f",  Util.getSymbol(currency), totalPrice));
    }

    private void confirmPurchase(){
        if (CustomerProfile.getCustomerProfile().getEmail().equals(CustomerProfile.CUSTOMER_ANONYMOUS)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            AppDatabase.getInstance(this).shoppingCartDao().deleteProductsByCustomer(CustomerProfile.getCustomerProfile().getEmail());
            Toast.makeText(this, getResources().getString(R.string.purchase_finish), Toast.LENGTH_SHORT).show();
        }
    }

    public void removeProduct(String modelId, String variantId) {
        ShoppingCartProduct productToRemove = mShoppingCartRecyclerListAdapter.getItems().stream().filter(p -> p.getModelId().equals(modelId) && p.getVariantId().equals(variantId)).findFirst().orElse(null);
        if (productToRemove != null) {
            mShoppingCartRecyclerListAdapter.getItems().remove(productToRemove);
            mShoppingCartRecyclerListAdapter.notifyDataSetChanged();
            updateTotalPrice();
            AppDatabase.getInstance(this).shoppingCartDao().deleteProductsByCustomerAndIds(CustomerProfile.getCustomerProfile().getEmail(), modelId, variantId);
            if (mShoppingCartRecyclerListAdapter.getItemCount() == 0) {
                confirmPurchase.setEnabled(false);
            }
        }
    }

    public void changeQyt(String modelId, String variantId, int qty) {
        ShoppingCartProduct productToModify = mShoppingCartRecyclerListAdapter.getItems().stream().filter(p -> p.getModelId().equals(modelId) && p.getVariantId().equals(variantId)).findFirst().orElse(null);
        if (productToModify != null) {
            if (productToModify.getQty() < qty) {
                productToModify.setQty(qty);
                mShoppingCartRecyclerListAdapter.notifyDataSetChanged();
                updateTotalPrice();
            } else {
                productToModify.setQty(qty);
                mShoppingCartRecyclerListAdapter.notifyDataSetChanged();
                updateTotalPrice();
            }
        }

        ShoppingCartProduct shoppingCart = AppDatabase.getInstance(this).shoppingCartDao().findProductsByCustomerAndIds(CustomerProfile.getCustomerProfile().getEmail(), modelId, variantId);
        if (shoppingCart != null) {
            productToModify.setQty(qty);
            AppDatabase.getInstance(this).shoppingCartDao().insertProducts(shoppingCart);
        }
    }

    public void onRemoveProduct(String modelId, String variantId){
        removeProduct(modelId, variantId);
    }

    public void onChangeQyt(String modelId, String variantId, int qty){
        changeQyt(modelId, variantId, qty);
    }
}
