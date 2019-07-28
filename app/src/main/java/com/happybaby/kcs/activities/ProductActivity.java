package com.happybaby.kcs.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.activities.interfaces.ProductView;
import com.happybaby.kcs.adapters.SizesListAdapter;
import com.happybaby.kcs.bd.room.AppDatabase;
import com.happybaby.kcs.models.SizeModel;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.presenters.ProductPresenter;
import com.happybaby.kcs.restapi.gooco.responses.ResponseSize;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseActivity implements ProductView,
        View.OnClickListener, ListView.OnItemClickListener {

    public static String PARAM_STORE_ID = "storeId";
    public static String PARAM_PRODUCT_NAME = "productName";
    public static String PARAM_MODEL_ID = "modelId";
    public static String PARAM_FINAL_PRICE = "finalPrice";
    public static String PARAM_ORIGINAL_PRICE = "originalPrice";
    public static String PARAM_FINAL_PRICE_TYPE = "finalPriceType";
    public static String PARAM_CURRENCY = "currency";
    public static String PARAM_SKU = "sku";
    public static String PARAM_COMPOSITION = "composition";
    public static String PARAM_COLOR = "color";
    public static String PARAM_URL = "url";
    public static String PARAM_CARE = "care";
    public static String PARAM_IMAGES = "images";
    public static String PARAM_SIZES = "size";

    private String productName;
    private String shareUrl;
    private String storeId;
    private ListView sizesList;
    private SizesListAdapter sizesListAdapter;
    private PopupWindow sizesPopupWindow;
    private Integer finalPrice;
    protected MenuItem itemCart;
    protected TextView sizesButton;
    private TextView finalPriceText;
    private TextView originalPriceText;
    private ProductPresenter productPresenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, productPresenter.getNumberOfProducts().toString());
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setupToolbar();
        setTitle(productName);
        this.storeId = getIntent().getExtras().getString(PARAM_STORE_ID);
        this.shareUrl = getIntent().getExtras().getString(PARAM_URL);
        String selectedItem = getIntent().getExtras().getString(PARAM_MODEL_ID);
        Integer finalPrice = getIntent().getExtras().getInt(PARAM_FINAL_PRICE);
        ArrayList<ResponseSize> sizes = getIntent().getExtras().getParcelableArrayList(PARAM_SIZES);
        ArrayList<String> images = getIntent().getExtras().getStringArrayList(PARAM_IMAGES);
        String productName = getIntent().getExtras().getString(PARAM_PRODUCT_NAME);
        String currency = getIntent().getExtras().getString(PARAM_CURRENCY);
        Integer originalPrice = getIntent().getExtras().getInt(PARAM_ORIGINAL_PRICE);
        String finalPriceType = getIntent().getExtras().getString(PARAM_FINAL_PRICE_TYPE);
        String sku = getIntent().getExtras().getString(PARAM_SKU);
        String composition = getIntent().getExtras().getString(PARAM_COMPOSITION);
        String color = getIntent().getExtras().getString(PARAM_COLOR);
        String careUrlImage = getIntent().getExtras().getString(PARAM_CARE);

        productPresenter = new ProductPresenter(this, storeId, selectedItem,
                productName, originalPrice, finalPrice, images.get(0), currency, sizes);

        ImageView mainImage = findViewById(R.id.product_image);
        TextView skuTextsView =  findViewById(R.id.sku);
        TextView compositionText =  findViewById(R.id.text_composition);
        TextView colorText =  findViewById(R.id.color_text);
        ImageView careImage =  findViewById(R.id.care_image);
        ImageView shareImage =  findViewById(R.id.share_image);
        ImageView wishImage =  findViewById(R.id.wish_image);
        TextView shoppingGuideText =  findViewById(R.id.shopping_guide_text);
        TextView sizesGuideButton = findViewById(R.id.sizes_guide);
        ImageView sizesExpand = findViewById(R.id.sizes_expand);
        Button addButton = findViewById(R.id.button_add);
        sizesButton = findViewById(R.id.sizes);
        finalPriceText =  findViewById(R.id.final_price);
        originalPriceText =  findViewById(R.id.original_price);

        shareImage.setOnClickListener(this);
        wishImage.setOnClickListener(this);
        shoppingGuideText.setOnClickListener(this);
        sizesGuideButton.setOnClickListener(this);
        sizesExpand.setOnClickListener(this);
        sizesButton.setOnClickListener(this);

        if (productPresenter.isAddButtonEnabled()) {
            addButton.setOnClickListener(this);
        } else {
            addButton.setEnabled(false);
        }

        Picasso.with(this).load(images.get(0)).placeholder(ContextCompat.getDrawable(this, R.drawable.image_not_found))
                .error(ContextCompat.getDrawable(this, R.drawable.image_not_found)).into(mainImage);

        productPresenter.setPrices();

        skuTextsView.setText(sku);
        compositionText.setText(composition);
        colorText.setText(color);

        Picasso.with(this).load(careUrlImage.replace("http:","https:")).into(careImage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateCartIcon();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        productPresenter.unbindView();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.share_image) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, this.shareUrl);
            startActivity(Intent.createChooser(intent, "Share"));
        } else if (view.getId() == R.id.button_add) {
            productPresenter.addProduct();
        } else if (view.getId() == R.id.wish_image) {
            Toast.makeText(this, getResources().getString(R.string.wish_list_not_available), Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.sizes_guide) {
            Toast.makeText(this, getResources().getString(R.string.sizes_guide_not_available), Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.sizes || view.getId() == R.id.sizes_expand) {
            showSizePopup(false);
        } else if (view.getId() == R.id.shopping_guide_text) {
            Intent intent = new Intent(this, GeneralInfoActivity.class);
            intent.putExtra(GeneralInfoActivity.PARAM_TYPE, GeneralInfoActivity.Types.TYPE_SHOPPING_GUIDE.ordinal());
            intent.putExtra(GeneralInfoActivity.PARAM_STORE_ID, this.storeId);
            startActivity(intent);
        }
    }

    public void showSizePopup(boolean fromAdd) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View sizeLayout = inflater.inflate(R.layout.popup_size, null);

        sizesList = sizeLayout.findViewById(R.id.color_list);
        sizesList.setOnItemClickListener(this);

        productPresenter.loadSizeModel(fromAdd);

        sizesPopupWindow = new PopupWindow(
                sizeLayout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        sizesPopupWindow.setElevation(10.0f);

        View root = this.findViewById(R.id.root_layout);
        sizesPopupWindow.showAtLocation(root, Gravity.CENTER,0,0);
        sizesPopupWindow.setOutsideTouchable(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cart){
            productPresenter.onClickShoppingCart();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToShoppingCart() {
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }

    public void showEmptyCartMessage() {
        Toast.makeText(this, getResources().getString(R.string.empty_cart), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int sizeSelected, long l) {
        productPresenter.selectSize(sizeSelected);
    }

    public void updateCartIcon() {
        if (itemCart != null) {
            LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
            setBadgeCount(this, icon, productPresenter.getNumberOfProducts().toString());
        }
        Toast.makeText(this, getResources().getString(R.string.product_added), Toast.LENGTH_SHORT).show();
    }

    public void loadSizeModelFinished(List<SizeModel> items) {
        sizesListAdapter = new SizesListAdapter(this, items);
        sizesList.setAdapter(sizesListAdapter);
    }

    public void loadSelectionFinished(ResponseSize currentSize) {
        sizesButton.setText(currentSize.getName());
        sizesPopupWindow.dismiss();
    }

    public void setOriginalPrice(String finalPrice){
        originalPriceText.setText(finalPrice);
        originalPriceText.setPaintFlags(originalPriceText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void setFinalPrice(String finalPrice){
        finalPriceText.setText(finalPrice);
    }

    public Context getContext() {
        return this;
    }
}
