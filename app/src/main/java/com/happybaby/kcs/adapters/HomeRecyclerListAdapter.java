package com.happybaby.kcs.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.happybaby.kcs.R;
import com.happybaby.kcs.fragments.interfaces.CatalogListener;
import com.happybaby.kcs.restapi.gooco.responses.ResponseHome;
import com.happybaby.kcs.restapi.gooco.responses.ResponseHomeCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRecyclerListAdapter extends
        RecyclerView.Adapter<HomeRecyclerListAdapter.ViewHolder> {

    protected Activity activity;
    protected List<ResponseHomeCategory> homeCategories;
    protected CatalogListener changeStatusListener;

    public HomeRecyclerListAdapter(Activity activity, CatalogListener changeStatusListener, ResponseHome dataHome) {
        this.activity = activity;
        this.changeStatusListener = changeStatusListener;
        this.homeCategories = dataHome.getCategories();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageCategory;
        public TextView nameCategory;

        public ViewHolder(View v) {
            super(v);
            this.imageCategory = v.findViewById(R.id.category_image);
            this.nameCategory = v.findViewById(R.id.category_name);
        }
    }

    @Override
    public void onBindViewHolder(HomeRecyclerListAdapter.ViewHolder viewHolder, int position) {
        ResponseHomeCategory homeCategory = homeCategories.get(position);
        Picasso.with(activity).load(homeCategory.getImageUrl()).placeholder(ContextCompat.getDrawable(activity, R.drawable.image_not_found))
                .error(ContextCompat.getDrawable(activity, R.drawable.image_not_found)).into(viewHolder.imageCategory);
        viewHolder.nameCategory.setText(homeCategory.getLabel());
        viewHolder.imageCategory.setOnClickListener(View -> {
                changeStatusListener.onChangeCategory(homeCategory.getCategoryId(), homeCategory.getLabel());
        });
    }

    @Override
    public HomeRecyclerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_home_recycler_list, viewGroup, false);
        return new HomeRecyclerListAdapter.ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return homeCategories.size();
    }

    public List<ResponseHomeCategory> getItems() {
        return this.homeCategories;
    }

    public void setWords(List<ResponseHomeCategory> words) {
        this.homeCategories = words;
    }

}