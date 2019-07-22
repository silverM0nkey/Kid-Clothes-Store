package com.happybaby.kcs.adapters;

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
import com.happybaby.kcs.fragments.CatalogFragment;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProduct;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProductsResults;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsRecyclerListAdapter extends
        RecyclerView.Adapter<ProductsRecyclerListAdapter.ViewHolder> {

    protected List<ResponseProduct> products;
    protected CatalogFragment catalogFragment;

    public ProductsRecyclerListAdapter(CatalogFragment catalogFragment, List<ResponseProduct> products) {
        this.catalogFragment = catalogFragment;
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
        ResponseProduct dir = products.get(position);
        Picasso.get().load(dir.getImages().get(0)).fit()
                .placeholder(ContextCompat.getDrawable(catalogFragment.getActivity(), R.drawable.image_not_found))
                .error(ContextCompat.getDrawable(catalogFragment.getActivity(), R.drawable.image_not_found))
                .into(viewHolder.imageProduct);
        viewHolder.nameProduct.setText(dir.getName());

        if (dir.getOriginalPrice() !=  null &&  !dir.getOriginalPrice().equals(dir.getFinalPrice())) {
            viewHolder.originalPrice.setText(String.format("€ %s", Float.valueOf(dir.getOriginalPrice()/100).toString()));
            viewHolder.originalPrice.setPaintFlags(viewHolder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        viewHolder.finalPrice.setText(String.format("€ %s", Float.valueOf(dir.getFinalPrice().floatValue()/100).toString()));

        viewHolder.wish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(catalogFragment.getActivity(), catalogFragment.getActivity().getResources().getString(R.string.wish_list_not_available), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.imageProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                catalogFragment.showProduct(dir.getModelId());
            }
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