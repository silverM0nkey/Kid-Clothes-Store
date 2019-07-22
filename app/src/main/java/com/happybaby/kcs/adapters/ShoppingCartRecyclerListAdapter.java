package com.happybaby.kcs.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.happybaby.kcs.R;
import com.happybaby.kcs.activities.ShoppingCartActivity;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoppingCartRecyclerListAdapter extends
        RecyclerView.Adapter<ShoppingCartRecyclerListAdapter.ViewHolder> {

    protected Context context;
    protected List<ShoppingCartProduct> products;
    protected ShoppingCartActivity shoppingCartActivity;


    public ShoppingCartRecyclerListAdapter(Context context, ShoppingCartActivity shoppingCartActivity, List<ShoppingCartProduct> shoppingCart) {
        this.context = context;
        this.shoppingCartActivity = shoppingCartActivity;
        this.products = shoppingCart;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageProduct;
        public TextView nameProduct;
        public TextView sizeProduct;
        public TextView qty;
        public ImageView lessQty;
        public ImageView moreQty;
        public TextView finalPrice;
        public ImageView removeItem;

        public ViewHolder(View v) {
            super(v);
            this.imageProduct = v.findViewById(R.id.product_image);
            this.nameProduct = v.findViewById(R.id.product_name);
            this.qty  = v.findViewById(R.id.qty);
            this.finalPrice = v.findViewById(R.id.final_price);
            this.sizeProduct = v.findViewById(R.id.size_product);

            this.lessQty =  v.findViewById(R.id.less_qty);
            this.moreQty = v.findViewById(R.id.more_qty);
            this.removeItem = v.findViewById(R.id.remove_item);
        }
    }

    @Override
    public void onBindViewHolder(ShoppingCartRecyclerListAdapter.ViewHolder viewHolder, int position) {
        ShoppingCartProduct dir = products.get(position);
        Picasso.get().load(dir.getUrlImage()).fit()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.image_not_found))
                .error(ContextCompat.getDrawable(context, R.drawable.image_not_found))
                .into(viewHolder.imageProduct);
        viewHolder.nameProduct.setText(dir.getName());
        viewHolder.finalPrice.setText(String.format("€ %s", Float.valueOf(dir.getFinalPrice().floatValue()/100).toString()));
        viewHolder.qty.setText(dir.getQty().toString());
        viewHolder.sizeProduct.setText(String.format("%s %s", context.getResources().getString(R.string.size), dir.getSize()));

        viewHolder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartActivity.removeProduct(dir.getModelId(), dir.getVariantId());
            }
        });

        viewHolder.moreQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartActivity.setQyt(dir.getModelId(), dir.getVariantId(), dir.getQty() + 1);
            }
        });

        viewHolder.lessQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dir.getQty() > 1) {
                    shoppingCartActivity.setQyt(dir.getModelId(), dir.getVariantId(), dir.getQty() - 1);
                }
            }
        });
    }

    @Override
    public ShoppingCartRecyclerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_shopping_cart_recycler_list, viewGroup, false);
        return new ShoppingCartRecyclerListAdapter.ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public List<ShoppingCartProduct> getItems() {
        return this.products;
    }

    public void setItems(List<ShoppingCartProduct> products) {
        this.products = products;
    }

}