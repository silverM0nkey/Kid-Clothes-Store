package com.happybaby.kcs.activities;

import android.content.Context;
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
import com.happybaby.kcs.activities.interfaces.ShoppingCartView;
import com.happybaby.kcs.adapters.ShoppingCartRecyclerListAdapter;
import com.happybaby.kcs.presenters.ShoppingCartPresenter;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;

import java.util.List;

import javax.inject.Inject;


public class ShoppingCartActivity extends BaseActivity implements ShoppingCartView {

    private TextView totalProducts;
    private TextView total;
    private Button confirmPurchase;
    RecyclerView shoppingCartRecyclerView;
    private ShoppingCartRecyclerListAdapter shoppingCartRecyclerListAdapter;
    private ShoppingCartPresenter shoppingCartPresenter;

    @Inject
    Context context;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        this.shoppingCartPresenter = new ShoppingCartPresenter(this);

        setupToolbar();
        setTitle(getResources().getString(R.string.shopping_cart_tittle));

        shoppingCartRecyclerView = findViewById(R.id.products);
        totalProducts = findViewById(R.id.total_products);
        total = findViewById(R.id.total);

        confirmPurchase = findViewById(R.id.accept);
        confirmPurchase.setOnClickListener(View -> {
            this.shoppingCartPresenter.confirmPurchase();
        });

        shoppingCartPresenter.loadShoppingCart();
        shoppingCartPresenter.updateTotalPrice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shoppingCartPresenter.unbindView();
    }

    public void notifyDataSetChanged() {
        shoppingCartRecyclerListAdapter.notifyDataSetChanged();
    }

    public void loadFinished(List<ShoppingCartProduct> shoppingCartProducts) {
        shoppingCartRecyclerListAdapter = new ShoppingCartRecyclerListAdapter(this,
                shoppingCartPresenter, shoppingCartProducts);
        shoppingCartRecyclerView.setAdapter(shoppingCartRecyclerListAdapter);
        shoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.recycler_view_divider));
        shoppingCartRecyclerView.addItemDecoration(itemDecorator);
    }

    public void confirmPurchaseEnabled(boolean enabled) {
        confirmPurchase.setEnabled(enabled);
    }

    public void setTotalProducts(String sTotalProducts) {
        totalProducts.setText(sTotalProducts);
    }

    public void setTotal(String sTotal) {
        total.setText(sTotal);
    }

    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void showPurchaseMessage() {
        Toast.makeText(this, getResources().getString(R.string.purchase_finish), Toast.LENGTH_SHORT).show();
    }

    public Context getContext() {
        return this;
    }
}
