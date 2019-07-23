package com.happybaby.kcs.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.fragments.interfaces.CatalogListener;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProduct;
import com.happybaby.kcs.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsRecyclerListAdapter extends
        RecyclerView.Adapter<ProductsRecyclerListAdapter.ViewHolder> {

    protected List<ResponseProduct> products;
    protected CatalogListener changeStatusListener;
    protected Context context;

    public ProductsRecyclerListAdapter(Context context, CatalogListener changeStatusListener, List<ResponseProduct> products) {
        this.context = context;
        this.changeStatusListener = changeStatusListener;
        this.products = products;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageProduct;
        public ImageView wish;
        public TextView nameProduct;
        public TextView originalPrice;
        public TextView finalPrice;

        public ViewHolder(View v) {
            super(v);
            this.wish = v.findViewById(R.id.wish_image);
            this.imageProduct = v.findViewById(R.id.product_image);
            this.nameProduct = v.findViewById(R.id.product_name);
            this.originalPrice = v.findViewById(R.id.original_price);
            this.finalPrice = v.findViewById(R.id.final_price);
        }
    }

    @Override
    public void onBindViewHolder(ProductsRecyclerListAdapter.ViewHolder viewHolder, int position) {
        ResponseProduct product = products.get(position);
        Picasso.with(context).load(product.getImages().get(0)).fit()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.image_not_found))
                .error(ContextCompat.getDrawable(context, R.drawable.image_not_found))
                .into(viewHolder.imageProduct);
        viewHolder.nameProduct.setText(product.getName());

        if (product.getOriginalPrice() !=  null &&  !product.getOriginalPrice().equals(product.getFinalPrice())) {
            viewHolder.originalPrice.setText(String.format("%s %s", Util.getSymbol(product.getCurrency()), Float.valueOf(product.getOriginalPrice().floatValue()/100).toString()));
            viewHolder.originalPrice.setPaintFlags(viewHolder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        viewHolder.finalPrice.setText(String.format("%s %s", Util.getSymbol(product.getCurrency()), Float.valueOf(product.getFinalPrice().floatValue()/100).toString()));

        viewHolder.wish.setOnClickListener(View -> {
                Toast.makeText(context, context.getResources().getString(R.string.wish_list_not_available), Toast.LENGTH_SHORT).show();
        });

        viewHolder.imageProduct.setOnClickListener(View -> {
                changeStatusListener.onShowProduct(product.getModelId());
        });
    }

    @Override
    public ProductsRecyclerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product_recycler_list, viewGroup, false);
        return new ProductsRecyclerListAdapter.ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public List<ResponseProduct> getItems() {
        return this.products;
    }

    public void setProducts(List<ResponseProduct> products) {
        this.products = products;
    }

}